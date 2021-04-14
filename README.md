# grocery-store
A simple grocery store application written in Kotlin/Spring.

## Prerequisites
- JRE >= 11
- Customized DNS entries
  - DNS entries pointing `127.0.0.1` must be in placed within system's `hosts` file.
  - Arbitrary domain names can be picked, although they must share common hostname and should only differ in their subdomains.
    ```
    ...
    127.0.0.1 fruit.grocery.store
    127.0.0.1 vegetable.grocery.store
    ...
    ```

## How to run
```sh
$ git clone https://github.com/devunt/grocery-store
$ cd grocery-store
$ ./gradlew bootRun
```

## TODO
- [ ] Provide comprehensive testcases
- [ ] Improve error handling
