
# java-game-character

# 🎮 Data Abstraction in Java (DSA Lesson)

## 📌 What is Data Abstraction?

- **Abstraction** is the process of showing only the essential details and hiding the implementation.
- In **OOP with Java**, abstraction is usually implemented using **abstract classes** and **interfaces**.
- It lets us focus on _what_ an object does, not _how_ it does it.

---

## 🐾 Example: Game Characters with Abstraction

We define an **abstract class** `GameCharacter` that all characters must extend.  
It forces every character to implement `attack()` and `specialMove()`, but allows unique behaviors too.

---

## 🕹️ Sample Output

```
Character: Arthas
Arthas swings a mighty sword!
Arthas uses Shield Bash!

Character: Merlin
Merlin casts a fireball!
Merlin uses Lightning Storm!
Merlin unleashes the Secret Power: Utot 💨!!!
```

---

## 📝 Summary

- **Abstraction** = “Define what, hide how.”
- Abstract classes/interfaces define the **rules**.
- Subclasses provide the **details**.
- Our game example makes it clear:
  - `GameCharacter` = abstract base
  - `Warrior`, `Mage` = concrete implementations
  
  Furthermore the java enhanced was to make like a pokemon battle where you get to use your attack special move heal and run
  as you can see when turning the GUI on. Additionally that when you run, you can't run just like in a pokemon battle and the enemy can attack you but you can heal and have colors.
