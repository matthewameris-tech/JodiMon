# Turn-Based Monster Battle Game (Java)

A complete turn-based monster arena combat game inspired by classic Pokémon battles. Built from the ground up in modern Java, featuring elemental affinities, critical hits, battle items, monster evolutions, intelligent AI rivals, and a 4-round Championship Tournament.

---

## 🌟 Gameplay & Features

### 1. Monster Roster & Starters
Choose your initial partner monster at Professor Oak's lab:
* **Ignispark** (Fire): High Attack & Speed. Signature Passive: **Blaze** (+50% Fire damage when HP < 35%). Evolves into **Pyrodrake** at Lv. 8 and learns **Inferno Blast**.
* **Aquashell** (Water): High Defense & HP. Signature Passive: **Torrent** (+50% Water damage when HP < 35%). Evolves into **Hydroclaw** at Lv. 8 and learns **Hydro Cannon**.
* **Leafox** (Grass): Balanced stats & sustain. Signature Passive: **Overgrowth** (+50% Grass damage when HP < 35%). Evolves into **Thornstalker** at Lv. 8 and learns **Solar Beam**.
* Additional unlockable species: **Voltrod** (Electric -> Sparkstrike) and **Boulderbug** (Earth -> Stonemaul).

### 2. Elemental Strengths & Weaknesses
Battle damage calculations faithfully reflect type matchups:
| Attacking Type | Super Effective (x2.0) | Not Very Effective (x0.5) | Immune (x0.0) |
|---|---|---|---|
| **Fire** | Grass | Water, Fire, Earth | - |
| **Water** | Fire, Earth | Water, Grass | - |
| **Grass** | Water, Earth | Fire, Grass | - |
| **Electric** | Water | Electric, Grass | Earth (x0.0) |
| **Earth** | Fire, Electric | Grass | - |
| **Normal** | Neutral (x1.0) | Neutral (x1.0) | - |

* **STAB (Same-Type Attack Bonus)**: Monsters using moves matching their type gain an extra **+50% damage bonus**!

### 3. Critical Hits & Damage Formula
* Damage follows the classic formula:
  $$\text{Damage} = \left(\frac{2 \times \text{Level} / 5 + 2 \times \text{Power} \times (\text{Attack} / \text{Defense})}{50} + 2\right) \times \text{TypeMult} \times \text{STAB} \times \text{Crit} \times \text{Variance}$$
* Standard attacks have a **10% chance** to land a Critical Hit (1.5x damage).
* High-crit moves (like *Flame Claw* and *Leaf Blade*) have a **25% chance** to crit!
* Random damage variance ranges between **0.85 and 1.00**.

### 4. Healing & Battle Items
Use items from your Bag during combat to turn the tide:
* **Potion**: Restores 30 HP.
* **Super Potion**: Restores 60 HP.
* **Max Potion**: Fully restores monster to maximum HP.
* **Revive**: Revives a fainted monster with 50% HP.
* **X-Attack**: Boosts active monster's Attack stage.

### 5. Monster Evolution System
* Monsters earn experience points (EXP) for knocking out opponent monsters.
* Reaching Level 8 triggers the **Monster Evolution Ceremony**.
* Evolution boosts base stats by **+30% to +35%**, transforms the monster's species and ASCII sprite, and teaches a devastating high-power evolution move!

### 6. Multi-Round Championship Tournament
Compete in a 4-round bracket against computer-controlled trainers:
1. **Quarterfinals**: vs. Scout Timmy (Grass) - Earns Super Potions & Revive.
2. **Semifinals**: vs. Gym Leader Terra (Earth & Electric) - Unlocks **Voltrod** (Electric) into your party!
3. **Finals**: vs. Ace Trainer Marina (Water & Fire) - Unlocks **Boulderbug** (Earth) into your party!
4. **Grand Championship**: vs. Champion Cynthia (3 high-level evolved monsters: Pyrodrake, Stonemaul, and Sparkstrike).

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
│           │   └── BattleEngine.java # Turn-based battle loop & resolution
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
│               ├── AsciiArt.java       # Sprites, health bars, and brackets
│               └── GameMenu.java       # Main menu and interactive loop
├── build.bat                     # Quick Windows build script
├── run.bat                       # Launch game interactive mode
├── run_tests.bat                 # Run test suite
└── README.md                     # Documentation
```

---

## 🚀 How to Run

### Option A: Using the provided batch scripts (Windows)
1. Double-click or run:
   ```cmd
   build.bat
   ```
2. Start playing:
   ```cmd
   run.bat
   ```
3. Run the automated test suite:
   ```cmd
   run_tests.bat
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
