# SemantiQ

SemantiQ is a customer support system powered by LLM (Large Language Models), developed as part of the fall 2023 software engineering project course. It encompasses source code for the server, frontend application, and project documentation.

## Project Structure

- **server**: Contains server files implemented in Java Spring.
- **app**: Includes frontend files written in JavaScript with React.
- **docs**: Holds project documentation.

## About the Project

### Motivation

SemantiQ is designed to address the motivation behind developing more powerful support center chatbots. The project aims to enhance conventional website chatbots by enabling them to communicate like humans and be available 24/7. This resolves issues of long queues or delays in email responses, allowing users to access support anytime. The project's selection and rationale are rooted in the understanding that many businesses and organizations, especially smaller local entities, struggle to provide efficient customer support. Existing chatbots often have limitations and fail to address complex queries or individual needs, resulting in customer frustration and inefficient support. 

The uniqueness of our project lies in leveraging large language models to enable human-like interactions. Users can create an account on our website and configure their own chatbot. By filling out a simple form, they specify the questions the bot should answer. Subsequently, they receive the code for their customized chatbot, which they can integrate into their website. SemantiQ has the potential to significantly enhance how businesses and organizations interact with their customers, providing efficient support and improving customer satisfaction, crucial in the era of increasing reliance on online customer support.

### Product Scope

The finalized product is a comprehensive web application seamlessly integrating the chatbot system and user control panel. It operates smoothly in browsers using a server-client architecture to offer users a seamless experience. The control panel system requires users to provide a verifiable email address and an internet connection for access. New users can easily create accounts by providing their email address, a compliant password, and then verifying their email via a security code. With our product, companies can create smarter chatbots through the control panel, capable of answering visitors' queries on their website. Users can input basic information about their business into a form and add additional sections and text data if needed. The size of text data is limited to a maximum of 32 MB. A user can update their chatbot a maximum of twice daily. Upon creating the bot, users receive the integration code for their website and can test it in the preview area. The chatbot system allows visitors to ask questions and search for answers. In case of dissatisfaction, they can contact a system administrator for further assistance. By purchasing or utilizing our product, you gain access to a fully functional system enabling the creation and implementation of intelligent chatbots on your website. The control panel's user interface allows you to manage your chatbot, monitor performance data, and assess via the dashboard. Additionally, you have access to the chat history, providing insights into past years' conversations with visitors on your website.

### Technical Infrastructure

The SemantiQ application is built on a client-server architecture and utilizes the following technologies:

#### Server-Side

- **Java Spring**: Framework used for server-side development. (JDK Development Kit 21.0.1)
- **MySQL**: Database management system for data storage.
- **Google SMTP (Simple Mail Transfer Protocol)**: Utilized for email functionality.

#### Client-Side

- **JavaScript**: Language used for client-side scripting.
- **React**: JavaScript library used for building the user interface.
- **Node.js**: JavaScript runtime for executing JavaScript code on the server.

These technologies have been employed to create a robust and efficient system that powers the SemantiQ application, facilitating seamless communication between users and the intelligent chatbot system.

## How to Run

1. **Create a Local MySQL Database**:
   - Create a database named `db_semantiq`.
   - (Provide necessary steps for database setup.)

2. **Run the Database**:
   - Run the database on `localhost:3306` or set the desired port in `application.properties`.

3. **Set Application Properties**:
   - Navigate to `server/src/main/resources/application.properties`.
   - Set the following fields:
     ```
     spring.datasource.username=
     spring.datasource.password=
     spring.mail.username=
     spring.mail.password=
     ```
   - Set your email credentials and [get an application password for Google SMTP](https://myaccount.google.com/apppasswords).

4. **Install Maven**:
   - [Install Maven](https://mkyong.com/maven/how-to-install-maven-in-windows/) (provide installation instructions).

5. **Build and Start Server**:
   - In the server directory, run `mvn package` to build the project.
   - Run `java -jar target/server-0.0.1-SNAPSHOT.jar` to start the server.
   - Alternatively, the **server** folder can be opened with an IDE (exp. Intellij idea) then built and run.

6. **Install Node.js**:
   - [Install Node.js](https://nodejs.org/en/download/).

7. **Install Dependencies for React App**:
   - In the `/app` directory,
   - Install dependencies by running `npm install`.

8. **Run the Application**:
   - After installing dependencies, execute `npm run start` in the app directory.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
