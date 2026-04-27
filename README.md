# LRU Cache — Java

An O(1) Least Recently Used cache implementation using a HashMap for key lookup and a doubly linked list for usage ordering. Built with pure Java — no Maven, no external dependencies.

## How It Works

```
  ┌──────────────────────────────────────────────────┐
  │                  LRU Cache                        │
  │                                                   │
  │  HashMap<Key, Node>     Doubly Linked List        │
  │  ┌─────┬──────┐        HEAD ↔ [A] ↔ [B] ↔ TAIL  │
  │  │  K  │ Node │              ▲                    │
  │  ├─────┼──────┤         most recent               │
  │  │  A  │  →───┼──────────┘                        │
  │  │  B  │  →───┼──────────────┘                    │
  │  └─────┴──────┘                                   │
  └──────────────────────────────────────────────────┘

  get(key)  → O(1) lookup + move to front
  put(key)  → O(1) insert at front, evict from tail if full
```

## Complexity

| Operation | Time | Space |
|-----------|------|-------|
| `get(key)` | O(1) | — |
| `put(key, value)` | O(1) | — |
| Overall | — | O(capacity) |

## Run

```powershell
.\run.ps1
```

## Run Tests

```powershell
.\run.ps1 -Test
```

## Example Output

```
get(1) = 10      # key 1 was inserted
get(2) = -1      # key 2 was evicted (capacity = 2, key 3 pushed it out)
get(3) = 30      # key 3 is present
Cache size: 2
```

## Project Structure

```
├── src/
│   ├── main/java/com/roadmap/lru/
│   │   ├── LRUCache.java          # Core cache with HashMap + doubly linked list
│   │   └── Main.java              # Demo driver
│   └── test/java/com/roadmap/lru/
│       └── LRUCacheTest.java      # Unit tests (eviction, update, size)
├── run.ps1                         # Compile & run script
└── README.md
```

## Key Concepts

- **Data structures**: HashMap + doubly linked list for O(1) operations
- **Eviction policy**: least recently used entry removed when capacity exceeded
- **Clean OOP**: sentinel nodes, encapsulated node management

## License

MIT