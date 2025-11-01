# PokeAPI-JavaApp — Project Documentation

---

## Project overview

**PokeAPI-JavaApp** is a Java desktop console client (Ant/NetBeans project) that consumes the public PokeAPI ([https://pokeapi.co](https://pokeapi.co)). The project demonstrates how to fetch, parse and present Pokémon data using a clean, object-oriented design. It includes features such as:

* Fetch Pokémon by name or ID
* Fetch a random Pokémon
* List Pokémon by type and by generation
* Basic in-memory caching to avoid repeated HTTP requests
* Structured Java model classes for `Pokemon`, `Ability`, `Type`, `Stat`

This document explains how each component works, how to use the app, and a roadmap to scale toward a Pokedex-style UI.

---

## Repository layout (recommended)

```
PokeAPI-JavaApp/
├── nbproject/
├── src/
│   └── pokeapi/
│       ├── api/
│       │   └── PokemonAPIClient.java
│       ├── model/
│       │   ├── Pokemon.java
│       │   ├── Ability.java
│       │   ├── Type.java
│       │   └── Stat.java
│       ├── parser/
│       │   └── PokemonParser.java
│       ├── utils/
│       │   └── CacheManager.java
│       └── main/
│           └── Main.java
├── lib/ (external jars e.g. org.json.jar)
├── build.xml
└── README.md
```

---

## Components and responsibilities

### 1. `pokeapi.api.PokemonAPIClient`

**Responsibility:** perform HTTP requests against PokeAPI endpoints and return raw JSON responses. It centralizes network logic and exposes higher-level convenience methods: `getPokemonData`, `getRandomPokemonData`, `getPokemonByType`, `getPokemonByGeneration`.

Key implementation points:

* Uses `HttpURLConnection` for HTTP GET requests.
* Adds a `User-Agent` header to avoid some server restrictions.
* Implements basic timeouts (`connect` & `read`).
* Uses `CacheManager` to store and reuse responses for the same endpoint.
* Keeps a `MAX_POKEMON` constant that defines the current assumed highest Pokémon ID (used for random selection).

**Endpoints used:**

* `/pokemon/{id or name}` — fetch single Pokémon
* `/type/{typeName}` — fetch list of Pokémon for a type
* `/generation/{id}` — fetch species for a generation

### 2. `pokeapi.utils.CacheManager`

**Responsibility:** simple in-memory cache used to store raw JSON responses keyed by endpoint URL.

Design notes:

* Very small, thread-unsafe `HashMap<String,String>` (fine for single-threaded or CLI use).
* Keys are normalized to lowercase to avoid duplicates.
* Methods: `save(key, data)`, `get(key)`, `contains(key)`, `clear()`.

Recommendations to scale:

* For a GUI or server, replace with a thread-safe `ConcurrentHashMap` and add TTL (time-to-live) eviction.
* Optionally implement a disk-backed cache (simple file storage or SQLite) for offline use.

### 3. `pokeapi.parser.PokemonParser`

**Responsibility:** parse the raw JSON returned by PokeAPI and produce a typed `Pokemon` object.

Key parsing responsibilities currently implemented:

* Extract `id`, `name`, `height`, `weight`, `base_experience`
* Parse `abilities` into `List<Ability>` (including hidden flag)
* Parse `types` into `List<Type>` (includes `slot` to preserve ordering)
* Parse `stats` into `List<Stat>` (maps `base_stat` values)
* Extract primary front sprite URL (`sprites.front_default`)

Notes:

* Parser uses `org.json` (JSONObject/JSONArray), but could be swapped to `Gson` or `Jackson` for automatic POJO mapping.
* The parser currently omits nested fields like evolution chains, moves list, held items and versions to keep the model compact. These can be added later as needed.

### 4. `pokeapi.model` (domain classes)

**Classes:**

* `Pokemon` — high level container (id, name, height, weight, baseExperience, abilities, types, stats, imageUrl)
* `Ability` — `name`, `isHidden` and friendly `toString()` (e.g. `Static (Hidden)`)
* `Type` — `name`, `slot` for ordering
* `Stat` — `name`, `baseValue`

Design benefits:

* Clear separation of concerns; small single-responsibility classes.
* Easy to extend: adding fields like `flavorText`, `evolutionChain`, `moves` or `generation` only requires adding properties and updating the parser.

### 5. `pokeapi.main.Main` (CLI)

**Responsibility:** provide a simple interactive CLI for demonstration and testing. The menu supports:

* Searching by name or ID
* Random Pokémon
* Listing by type (top 10 preview)
* Listing by generation (top 10 preview)

Display features:

* Colorized basic output using ANSI escape sequences (works in many terminals)
* Nicely formatted `printPokemonInfo` method that prints abilities, types and stats

---

## How to run (developer instructions)

1. Ensure you have Java 11+ installed (JDK 11 or newer recommended).
2. Import project into NetBeans as an Ant project.
3. Put `org.json` jar (or your preferred JSON library) into `lib/` and add it to project Libraries.
4. Clean and Build the project.
5. Run `Main` from NetBeans or use Ant target `run`.

Example CLI usage:

* Choose `1` and enter `pikachu` → show Pikachu summary
* Choose `2` → fetch and show a random Pokémon
* Choose `3` and enter `fire` → show a few fire-type Pokémon
* Choose `4` and enter `1` → show Pokémon species from generation 1

---

## Error handling & edge cases

Current behavior:

* Network errors print a short message and return an empty string.
* Parser assumes required fields exist and may throw runtime exceptions if the structure is unexpected.

Improvements recommended:

* Add explicit error objects or exceptions from `PokemonAPIClient` (e.g., custom `ApiException`) so callers can react differently to 404 vs 500 vs network timeouts.
* Add defensive checks in `PokemonParser` using `optString` / `optInt` or try/catch to avoid crashing when optional fields are missing.

---

## Caching: current design and improvements

**Current:** in-memory HashMap keyed by full endpoint URL. Good for short CLI sessions.

**To improve:**

* Add TTL (time-to-live) and maximum size eviction (LRU) — use `LinkedHashMap` or Guava Cache.
* Make the cache pluggable (interface + in-memory/disk implementations).
* For UI, implement background cache warming for lists to avoid blocking the UI thread.

Example TTL approach (pseudo):

* Store `CacheEntry { String data; long timestamp }`
* On `get`, compare `timestamp + TTL` with current time and invalidate if expired.

---

## Random Pokémon: details

We pick a random integer between `1` and `MAX_POKEMON` (constant). The client then requests `/pokemon/{id}`. This is simple but:

* `MAX_POKEMON` should be updated when new generations are out.
* Some IDs may not have resources or may be omitted; handle 404s gracefully.

Better approach for true randomness:

* Fetch the full `pokemon?limit=100000&offset=0` endpoint once and cache the full list of names and urls; then pick a random index from that cached list. This avoids issues with gaps in IDs.

---

## Listing by Type and Generation — what to expect

* `/type/{typeName}` returns an object with an array `pokemon` where each item has a `pokemon` object with `name` and `url`. Use that to get names quickly.
* `/generation/{id}` returns `pokemon_species` (note: `pokemon_species` use species names and may map to different Pokemon numeric IDs). To get the actual default variant for a species, you might need an extra call to `/pokemon/{name}`.

Performance note: listing large types/generations can return hundreds of items — the current CLI limits output to the first 10 for readability. For the UI, support paging and lazy loading.

---

## Roadmap to scale into a Pokedex UI

This section explains how to evolve the CLI into a full Pokedex GUI (Swing or JavaFX).


### Data & performance strategies

* **Thumbnails & lazy images**: load low-resolution sprites first, then higher-resolution images. Cache images locally.
* **Batch requests**: where the API supports listing (type/generation), use the list response first then fetch details on demand.


### Example UI flow for detail page

1. User selects a Pokémon from list → show skeleton UI with basic info (name, thumbnail)
2. Kick off background tasks:

   * load full Pokémon details (`/pokemon/{name}`)
   * load species data (`/pokemon-species/{id}`) for flavor text and evolution info
   * load evolution chain (`/evolution-chain/{id}`)
3. When each task completes, update respective UI sections

---

## Packaging & distribution

* Ant builds a `dist/` folder with the main `.jar` and `dist/lib` containing dependent jars.
* For easy distribution, consider creating a platform-specific installer or a ZIP containing `lib/` and `app.jar`.
* For an easier dependency workflow, migrate to **Maven** or **Gradle** — they resolve jars automatically and simplify CI integration.

---

## Testing & quality

* Add unit tests for `PokemonParser` using sample JSON fixtures (store a small `fixtures/` folder with sample responses).
* Mock HTTP requests for `PokemonAPIClient` using a local test server or by wrapping the HTTP call into a small interface so it can be stubbed.
* Add integration tests that run in CI with a small subset of live queries (rate-limited) or use cached fixtures.

---

## Security, rate limits and API etiquette

* PokeAPI is public but rate-limited. Do not hammer endpoints in loops.
* Respect caching and batch requests. Add small delays if performing many requests.
* If you expand the app (especially for a web/hosted version), consider adding retry logic with exponential backoff for transient errors.

---

## Credits & references

* PokeAPI: [https://pokeapi.co](https://pokeapi.co)
* JSON library used: `org.json` (or optionally `Gson`/`Jackson`)

---

