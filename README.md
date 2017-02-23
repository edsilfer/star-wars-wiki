<a name="summary">
# Summary
1. [Overview](#overview) 
   * [Goal](#goal) 
   * [Requirements](#requirements) 
   * [Showcase](#showcase) 
   * [QR Code Samples](#qrcode-examples) 
2. [Architecture](#architecture) 
   * [Design Patterns](#design-patterns) 
   * [Frameworks](#frameworks) 
   * [End Points](#end-points) 
4. [License](#license)


<a name="overview" />
# Overview
<a name="goal" />
## Goal
Create an application capable to read urls from Star Wars API, displaying these information in an application of type master-details. The requirements raised for this assignment shall test concepts of:
 1. Architectural design for Android Applications;
 2. Ability to communicate with complex end-points, parsing JSON responses and update UI;
 3. Data persistence and database manipulation;
 4. Sensor manipulation (camera);
 5. Permission requirement lifecycle;
 6. other.

<a name="requirements" />
## Requirements

|   #  |Requirement                            |Description                                                                                                                                                                                                                           |
|------|---------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|REQ001|Read QR Code                           | Application must be able to read an URL from a QR Code image, storing the collected data locally
|REQ002|Display character list                 | Display to the user a list with all read characters containing, at least, character’s name and URL
|REQ003|Display user details                   | When click on a list item, application must display character’s details including a list of movies that that character was part of. Among the details, show: User’s geolocation at the time when that QR Code was read; Film’s poster;
|REQ004|Collect user’s geolocation             | Store user’s geolocation on local database at the moment that the QR Code is read
|REQ005|Allow user to navigate to film website | When user taps on movie’s poster, application must redirect the user to film’s website
|REQ006|Allow information caching              | All information once loaded from web into app must be persisted locally and accessible offline

<a name="showcase" />
## Showcase

Gif

<a name="qrcode-examples" />
## QR Code Samples


Figure 01: sample qr codes generated on: http://br.qr-code-generator.com/ 

Individual image can be found [here]()..

<a name="architecture" />
# Architecture
The application follows Model-View-Presenter (MVP) pattern:
Model: layer that holds classes that describe Business Objects. As an ORM (see Realm) is being used, these classes also acts as template for generating the database. Despite these core business objects, model layer also provides enums that helps controlling the application logical flow in an elegant way and dictionaries. Dictionaries are POJO classes that matches the structure of the JSON returned as response from different API requests. Once filled, the dictionary can be dumped into a core object for persistence. In some cases, the need of this extra layer can be ignored, however, with the current limitation from Realm, it is an easy workaround that do not add overhead on the actual scenario;
View: the view layer is passive - as per article on Martin Fowler’s article - it means, it doesn’t contain business rules that decide when to trigger UI changes but, instead, it does perform the action of triggering these changes. This layer is composed by Activities, Fragments, Adapters and the associate XML, .mnu, and other resource files that they bind to. All view classes passes the events derived from user’s interaction with the UI to the Presenter layer, responsible for processing the business rules that will in turn update the UI; 
Presenter: this layer is the mean term among business rules and view layer. It can be, in some cases, the messenger that deliver UI events to be processed by business objects on model layer or, it can process them itself. In this application, the Presenter layer is responsible to execute business rules and also trigger their outcome to view layer. Network event handling, must be executed on this layer.

Below there is a sequence diagram that shows the basic configuration of model and presenter layers on a typical activity or fragment:


<a name="design-patterns" />
## Design Patterns
 - **Singleton**: this pattern is used mainly to offer a single instance of presenter for its respective views;
 - **Observer**: this pattern is largely used in order to emit network events that trigger database actions or UI updates. The variant employed is observed base on bus, being all call routed to a class that holds reference to all subscribers;
 - **Delegate**: this pattern is used in more than one place in the, for instance, with Postman and Router classes. The idea is to decouple classes that play distinct roles in the app - as the ones responsible for network communication for instance. That way, if any maintenance is required on them is not trigger beyond the class that gather all calls;

<a name="frameworks" />
## Frameworks
 - [**Kotlin**](https://kotlinlang.org/): Kotlin is free to use and owned by Jet Brains. It adds a lot of cool features, boosting your productiveness while keeping everythying 100% compatible with Java;
 - [**Realm Mobile Database**](https://realm.io/products/realm-mobile-database/): _“(...) Realm Mobile Database is an alternative to SQLite and Core Data. Thanks to its zero-copy design, Realm Mobile Database is much faster than an ORM, and often faster than raw SQLite. Get started in minutes, not hours…”_
 - [**Retrofit**](https://square.github.io/retrofit/): _"(...) A type-safe HTTP client for Android and Java;
     - [**OkHttp**](http://square.github.io/okhttp/): “(...) HTTP is the way modern applications network. It’s how we exchange data & media. Doing HTTP efficiently makes your stuff load faster and saves bandwidth.
       OkHttp is an HTTP client that’s efficient by default:
          - HTTP/2 support allows all requests to the same host to share a socket.
          - Connection pooling reduces request latency (if HTTP/2 isn’t available).
          - Transparent GZIP shrinks download sizes.
          - Response caching avoids the network completely for repeat requests.
        
        OkHttp perseveres when the network is troublesome: it will silently recover from common connection problems. If your service has multiple IP addresses OkHttp will attempt alternate addresses if the first connect fails. This is necessary for IPv4+IPv6 and for services hosted in redundant data centers. OkHttp initiates new connections with modern TLS features (SNI, ALPN), and falls back to TLS 1.0 if the handshake fails.
        Using OkHttp is easy. Its request/response API is designed with fluent builders and immutability. It supports both synchronous blocking calls and async calls with callbacks. OkHttp supports Android 2.3 and above. For Java, the minimum requirement is 1.7…”_
     - [**Gson**](https://github.com/google/gson): _“(...) A Java serialization/deserialization library that can convert Java Objects into JSON and back…”_
     - [**RxAndroid**](https://github.com/ReactiveX/RxAndroid): _“(...) ReactiveX is a combination of the best ideas from the Observer pattern, the Iterator pattern, and functional programming...”_
 - [**Dagger 2**](https://google.github.io/dagger/): a dependency Injector for Android and Java, used to grant one of the S.O.L.I.D. principles for OO programming (Dependency Inversion Principle). Besides allowing the high level class to not depend upon low level ones, it makes Unit Test easier to perform with the help of a mocking framework i.e. Mockito;
 - **Other**:
     - [**Data bind**](https://developer.android.com/topic/libraries/data-binding/index.html): “(...) The Data Binding Library offers both flexibility and broad compatibility — it's a support library, so you can use it with all Android platform versions back to Android 2.1 (API level 7+)...”

<a name="end-points" />
## End points
 - [**Star Wars API**](https://swapi.co/): _“(...) The Star Wars API, or "swapi" (Swah-pee) is the world's first quanitified and programmatically-accessible data source for all the data from the Star Wars canon universe! We've taken all the rich contextual stuff from the universe and formatted into something easier to consume with software. Then we went and stuck an API on the front so you can access it all!...”_
 - [**Google Custom Search**](https://developers.google.com/custom-search/): _“(...) Google Custom Search enables you to create a search engine for your website, your blog, or a collection of websites. You can configure your engine to search both web pages and images. You can fine-tune the ranking, add your own promotions and customize the look and feel of the search results. You can monetize the search by connecting your engine to your Google AdSense account…”_
 - [**The Movie DB**](https://www.themoviedb.org/): _“(...) The Movie Database (TMDb) is a community built movie and TV database. Every piece of data has been added by our amazing community dating back to 2008. TMDb's strong international focus and breadth of data is largely unmatched and something we're incredibly proud of. Put simply, we live and breath community and that's precisely what makes us different…”_

<a name="license" />
## License
Copyright 2017 Edgar da Silva Fernandes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
