# Salt GVA Java Client

Being a non speaking Valencian, sometimes I forget how to write exactly some words when speaking Valencian, that's why I created this Java application which connects with the server 
of https://salt.gva.es/es/traductor to translate directly from Spanish to Valencian whenever I want, and thus save spelling mistakes and refresh my Valencian.

In this application, I am using some of the really cool features from Java, like:
- Unnamed classes from Java 21, no more public static void main... No classes, only functions
- "var" keyword for concise naming
- Read/Write from the clipboard
- Use native notification system from Linux (if you are using a different OS feel free adapt it or open an issue/pr)
- Modern HTTP Client

## Getting Started

Authorization keys where extracted from https://salt.gva.es/es/traductor inspecting the CURL when doing a traduction request, no need for authentication tbh.
Make sure to have Java 21

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Guuri11/salt-client.git
   ```

2. **Compile and run the text editor:**
    ```bash
    # create the jar
    mvn clean package
    ```
    ```bash
    # run jar with java 21 using the flag --enable-preview
    java -jar --enable-preview target/salt-client-1.0-SNAPSHOT.jar
    ```
    
## Usage
Write a text with the prefix "salt:", for example "salt:hola mundo", copy the text, then you application will read the clipboard, connect to salt server, and return you the translated text, in this case it would be "hola món"

## Contributing

Contributions are welcome! If you have any ideas for improvements or find any issues, feel free to open an issue or submit a pull request.
