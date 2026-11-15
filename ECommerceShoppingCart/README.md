# E-Commerce Shopping Cart System

Final Project — Course **ShP-2216 Software Design Patterns**, Astana IT University (Group SE-2505).

## Team Members & Pattern Distribution
- **Matzhan Arlan**: Builder (`com.aitu.ecommerce.builder`), Strategy (`com.aitu.ecommerce.strategy`)
- **Kossaman Madiyar**: Decorator (`com.aitu.ecommerce.decorator`), Observer (`com.aitu.ecommerce.observer`)
- **Rymbek Beknur**: Facade (`com.aitu.ecommerce.facade`), Adapter (`com.aitu.ecommerce.adapter`)

## How to Run (IntelliJ IDEA or Terminal)
1. Open the `ECommerceShoppingCart_Updated` folder in **IntelliJ IDEA**.
2. Mark `src` as **Sources Root** (if not detected automatically) and select **JDK 17+**.
3. Run `com.aitu.ecommerce.Main`:
   - Without arguments: runs the automated end-to-end showcase of all 6 design patterns.
   - With `--interactive` program argument: runs the interactive console menu.

Terminal compilation & execution:
```bash
javac -d out $(find src -name "*.java")
java -cp out com.aitu.ecommerce.Main
java -cp out com.aitu.ecommerce.Main --interactive
```
