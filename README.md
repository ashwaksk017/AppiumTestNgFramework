A classic Appium testNG framework for android, and IOS, supports native and hyprid apps
Key Components:

Appium Java Client to interact with mobile elements.

Maven for dependency management.

Page Object Model for reusability and separation of concerns.

TestNG for test configuration, execution, and reporting.

ExtentReports for test reporting.

Page Object Model (POM): Ensured each screen was modular and reusable.

Factory Pattern: Used for initializing drivers across platforms.

Abstraction Layers: Created separate layers for test logic and business logic.

Environment Configs: Externalized via .properties or .yaml files for dynamic test environments.

Parallel Execution: Leveraged TestNG parallel="tests" and ThreadLocal to support concurrent sessions.
