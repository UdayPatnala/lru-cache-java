param(
    [switch]$Test
)

$ErrorActionPreference = "Stop"
$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$outMain = Join-Path $projectRoot "out\main"
$outTest = Join-Path $projectRoot "out\test"

if (Test-Path (Join-Path $projectRoot "out")) {
    Remove-Item -Recurse -Force (Join-Path $projectRoot "out")
}

New-Item -ItemType Directory -Force -Path $outMain | Out-Null

$mainSources = Get-ChildItem -Path (Join-Path $projectRoot "src\main\java") -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
javac -d $outMain $mainSources

if ($Test) {
    New-Item -ItemType Directory -Force -Path $outTest | Out-Null
    $testSources = Get-ChildItem -Path (Join-Path $projectRoot "src\test\java") -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
    javac -cp $outMain -d $outTest $testSources
    java -cp "$outMain;$outTest" com.roadmap.lru.LRUCacheTest
} else {
    java -cp $outMain com.roadmap.lru.Main
}