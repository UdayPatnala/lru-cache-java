# LRU Cache — Java

A professional, generic, and thread-safe O(1) Least Recently Used cache implementation using a `HashMap` for key lookup and a doubly linked list for usage ordering. Built with pure Java — no Maven, no external dependencies.

## Key Features (Interview Ready)

1. **Generics (`<K, V>`)**: Fully generic implementation, allowing you to cache any object type.
2. **Interface-Driven Design**: Implements a standard `Cache<K, V>` interface, demonstrating coding to an interface.
3. **Thread-Safety**: Provides a `ThreadSafeCache<K, V>` decorator utilizing `java.util.concurrent.locks.ReentrantLock` for safe concurrent access without modifying the core logic.
4. **O(1) Time Complexity**: Core operations `get()` and `put()` run in constant time.

## How It Works

```
  ┌──────────────────────────────────────────────────┐
  │                  LRU Cache                        │
  │                                                   │
  │  HashMap<K, Node<K, V>>   Doubly Linked List      │
  │  ┌─────┬──────┐        HEAD ↔ [A] ↔ [B] ↔ TAIL  │
  │  │  K  │ Node │              ▲                    │
  │  ├─────┼──────┤         most recent               │
  │  │ "A" │  →───┼──────────┘                        │
  │  │ "B" │  →───┼──────────────┘                    │
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

Includes a custom testing suite that verifies eviction, updates, generics, and concurrent thread-safety.

```powershell
.\run.ps1 -Test
```

## Example Output

```
--- Generic LRU Cache Demo ---
get('One') = 10
get('Two') = null
get('Three') = 30
Cache size: 2
```

## Project Structure

```
├── src/
│   ├── main/java/com/roadmap/lru/
│   │   ├── Cache.java             # Core cache interface
│   │   ├── LRUCache.java          # HashMap + doubly linked list implementation
│   │   ├── ThreadSafeCache.java   # ReentrantLock decorator for concurrency
│   │   └── Main.java              # Demo driver
│   └── test/java/com/roadmap/lru/
│       └── LRUCacheTest.java      # Unit tests (eviction, generics, concurrency)
├── run.ps1                        # Compile & run script
└── README.md
```

## License

MIT