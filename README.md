# Example Android App
A simple example android application using the conductor framework for screen flow, databinding for view access, retrofit for remote data access and Rx+LiveData for event propagation. Follows the MVVM pattern.

# Setup
1. Import the project into Android Studio
2. Setup your server (as instructed by [the linked repo](https://github.com/b00dle/example-python-server))
3. Copy the access token of your server (again... see Readme for [the linked repo](https://github.com/b00dle/example-python-server))
4. Open ```local.properties``` in the root directory and add this line ```api.token=<your-token-here>```
5. Sync Project with Gradle Files (see Android Studio manual)
6. Run your server
7. Run Android project on emulator (same machine as server)

If you wish to connect to a similar server outside of the emulator host machine, or want to deploy to a physical device, refer to the api path and token setup in your module level gradle file.