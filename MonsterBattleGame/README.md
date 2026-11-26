# JodiMon - Turn-Based Battle Game (Java)

A complete turn-based JodiMon battle arena combat game inspired by classic Pokémon battles. Built from the ground up in modern Java, featuring elemental affinities, critical hits, battle items, JodiMon evolutions, intelligent AI rivals, retro Game Boy Color terminal styling, and a 4-round Championship Tournament.

---

## 🌟 Gameplay & Features

### 1. JodiMon Roster & High-Quality Sprites
Choose your initial partner JodiMon at Professor Oak's lab, featuring expressive high-definition multi-line ASCII sprites and retro Game Boy Color elemental styling:
* **Ignispark** (Fire): Fiery puppy with flaming ears and curled tail. Signature Passive: **Blaze** (+50% Fire damage when HP < 35%). Evolves into the winged dragon **Pyrodrake** at Lv. 8 and learns **Inferno Blast**.
* **Aquashell** (Water): Armored turtle with carved carapace and water splash. Signature Passive: **Torrent** (+50% Water damage when HP < 35%). Evolves into the armored snapper **Hydroclaw** at Lv. 8 and learns **Hydro Cannon**.
* **Leafox** (Grass): Woodland fox with leaf plume tail and sprout crest. Signature Passive: **Overgrowth** (+50% Grass damage when HP < 35%). Evolves into the vine-blade predator **Thornstalker** at Lv. 8 and learns **Solar Beam**.
* Additional unlockable species: **Voltrod** (Electric -> Sparkstrike) and **Boulderbug** (Earth -> Stonemaul).

---

### 2. Elemental Strengths & Weaknesses
Battle damage calculations faithfully reflect type matchups:

| Attacking Type | Super Effective (x2.0) | Not Very Effective (x0.5) | Immune (x0.0) |
|:---:|:---:|:---:|:---:|
| **Fire** | Grass | Water, Fire, Earth | — |
| **Water** | Fire, Earth | Water, Grass | — |
| **Grass** | Water, Earth | Fire, Grass | — |
| **Electric** | Water | Electric, Grass | Earth (x0.0) |
| **Earth** | Fire, Electric | Grass | — |
| **Normal** | Neutral (x1.0) | Neutral (x1.0) | — |

* **STAB (Same-Type Attack Bonus)**: JodiMon using moves matching their elemental type gain an extra **+50% damage bonus**!

---

### 3. Critical Hits & Damage Formula
* Damage follows the classic formula:
  $$\text{Damage} = \left(\frac{2 \times \text{Level} / 5 + 2 \times \text{Power} \times (\text{Attack} / \text{Defense})}{50} + 2\right) \times \text{TypeMult} \times \text{STAB} \times \text{Crit} \times \text{Variance}$$
* Standard attacks have a **10% chance** to land a Critical Hit (1.5x damage).
* High-crit moves (like *Flame Claw* and *Leaf Blade*) have a **25% chance** to crit!
* Random damage variance ranges between **0.85 and 1.00**.

---

### 4. Healing & Battle Items
Use items from your Bag during combat to turn the tide:
* **Potion**: Restores 30 HP.
* **Super Potion**: Restores 60 HP.
* **Max Potion**: Fully restores monster to maximum HP.
* **Revive**: Revives a fainted monster with 50% HP.
* **X-Attack**: Boosts active monster's Attack stage.

---

### 5. JodiMon Evolution System
* JodiMon earn experience points (EXP) for knocking out opponent monsters.
* Reaching Level 8 triggers the **JodiMon Evolution Ceremony**.
* Evolution boosts base stats by **+30% to +35%**, transforms the JodiMon's species and ASCII sprite, and teaches a devastating high-power evolution move!

---

### 6. Multi-Round Championship Tournament
Compete in a 4-round bracket against computer-controlled trainers:
1. **Quarterfinals**: vs. Scout Timmy (Grass) — Earns Super Potions & Revive.
2. **Semifinals**: vs. Gym Leader Terra (Earth & Electric) — Unlocks **Voltrod** (Electric) into your party!
3. **Finals**: vs. Ace Trainer Marina (Water & Fire) — Unlocks **Boulderbug** (Earth) into your party!
4. **Grand Championship**: vs. Champion Cynthia (3 high-level evolved JodiMon: Pyrodrake, Stonemaul, and Sparkstrike).

---

## ☕ Core Java Concepts Demonstrated

| Concept | Implementation Details |
|---|---|
| **Classes & Encapsulation** | `Monster`, `Trainer`, `Move`, `Item`, `BattleEngine`, `Tournament`, with private/protected fields and clean public interfaces. |
| **Inheritance & Polymorphism** | Abstract `Monster` extended by `FireMonster`, `WaterMonster`, `GrassMonster`, `ElectricMonster`, `EarthMonster`. Abstract `Move` extended by `AttackMove` and `StatusMove`. Abstract `Item` extended by `HealingItem`, `ReviveItem`, and `BuffItem`. |
| **Methods** | Modular calculation methods (`takeDamage`, `heal`, `canEvolve`, `evolve`, `getMultiplierAgainst`, `decideAction`). |
| **Conditionals** | Type matchup matrices, accuracy checks, critical hit thresholds, AI decision evaluation trees, win/loss conditions. |
| **Random Numbers** | `java.util.Random` powering damage variance (85%-100%), accuracy misses, critical hit rolls, and AI move variety. |

---

## 📁 Project Structure

```text
MonsterBattleGame/
├── bin/                          # Compiled .class binaries
├── src/
│   └── com/
│       └── monsterbattle/
│           ├── Main.java         # Application entry point & demo flags
│           ├── GameTest.java     # Automated unit test suite
│           ├── battle/
│           │   └── BattleEngine.java # Turn-based battle loop & arena render
│           ├── data/
│           │   ├── MonsterFactory.java # Preconfigured monsters & moves
│           │   └── TrainerFactory.java # Preconfigured trainers & rosters
│           ├── model/
│           │   ├── ElementType.java    # Elemental enum & type chart
│           │   ├── Move.java           # Abstract move base class
│           │   ├── AttackMove.java     # Damaging moves & crit formulas
│           │   ├── StatusMove.java     # Buffs, debuffs, and healing moves
│           │   ├── Monster.java        # Base monster state & evolution logic
│           │   ├── FireMonster.java    # Fire subclass (Blaze passive)
│           │   ├── WaterMonster.java   # Water subclass (Torrent passive)
│           │   ├── GrassMonster.java   # Grass subclass (Overgrowth passive)
│           │   ├── ElectricMonster.java# Electric subclass (Static Surge)
│           │   ├── EarthMonster.java   # Earth subclass (Solid Rock)
│           │   ├── Item.java           # Abstract item base class
│           │   ├── HealingItem.java    # Restorative potions
│           │   ├── ReviveItem.java     # Monster revive item
│           │   ├── BuffItem.java       # Stat enhancers (X-Attack)
│           │   └── Trainer.java        # Player & AI decision logic
│           ├── tournament/
│           │   └── Tournament.java     # 4-stage Championship ladder
│           └── ui/
│               ├── Ansi.java           # Terminal colors & dynamic HP bars
│               ├── AsciiArt.java       # High-definition sprites & banners
│               └── GameMenu.java       # Main menu and interactive loop
├── build.bat                     # Windows compilation script
├── run.bat                       # Launch interactive game
├── run_tests.bat                 # Run automated test suite
├── README.md                     # Markdown documentation
└── read.me                       # Plain text / markdown documentation
```

---

## 🚀 How to Run

### Option A: Using the provided batch scripts (Windows)
1. **Compile the game**:
   ```cmd
   build.bat
   ```
2. **Start playing**:
   ```cmd
   run.bat
   ```
3. **Run automated test suite**:
   ```cmd
   run_tests.bat
   ```
4. **Watch self-playing battle demo**:
   ```cmd
   run.bat --demo
   ```

### Option B: Using the command line directly
From the `MonsterBattleGame` root directory:
```bash
# 1. Compile all Java sources
javac -d bin -sourcepath src src/com/monsterbattle/Main.java src/com/monsterbattle/GameTest.java

# 2. Run the interactive game
java -cp bin com.monsterbattle.Main

# 3. Or run the non-interactive automated battle demo
java -cp bin com.monsterbattle.Main --demo
```
