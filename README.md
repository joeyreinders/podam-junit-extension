# podam-junit-extension
This jUnit extension allows you to let Podam fill up properties that are annotated with @Podam.
## About
This plugin is not available on Maven Central as I'm not yet satisfied with the code quality and architecture. You can freely use the code, although I would appreciate that you fork this repository.
I'm not involved in the development of Podam, I just created this simple plugin to fulfill the requirements I had on project. 
## Prerequisits
This library works as of the following versions:
* jUnit >= `5.7.0`
* Podam >= `7.2.0.RELEASE` (https://mtedone.github.io/podam/index.html)
## Usage
### PodamExtension
You need to extend the testcase with PodamExtension. This to let jUnit know this extension is needed.
Code: `@ExtendWith(PodamExtension.class)`
### PodamProvider
You can present a custom Podam implementation via this annotation. For this add `@PodamProvider({your-podam-supplier-class}.class)`
See `CustomPodamProviderTest.java` for an example.
### @Podam
The `@Podam` annotation can be used on a property or on a constructor argument
It takes two optional arguments:
* `manufacturePojoWithFullData`, default false. Indicates if we use the `manufacturePojo` (false) or `manufacturePojoWithFullData` (true) function of `PodamFactory`
* `genericTypeArgs` fullfills exactly the same functionality as the argument on `PodamFactory` 
## Unsupported stuff
The following functions are not yet supported:
* @PodamAnnotation annotations
* Usage of Collections/Maps or Arrays. (This however is available within a POJO)


