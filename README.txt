To use the library, you must
1) Assemble package jar c command maven package
2) Connect the jar-library to the project
3) Mark annotated with @ Bean (singleton = ...), all classes, visible mechanism
4) Mark the field, which is expected to dependency injection, annotation @ InjectedBean
5) Create an instance of BeanFactory bf = BeanFactory.create ();
6) Call the bf.lookup (SomeClass.class) for the implementation of all the necessary dependencies