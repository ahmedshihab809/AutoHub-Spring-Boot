# AutoHub-Spring-Boot

AutoHub is a centralized platform that provides users with access to various workshops. This application is built using the Spring Boot framework, with HTML5, CSS, and Thymeleaf used for the frontend. Additionally, AutoHub leverages the Google Maps API for location detection.

## Features

- **Workshop Centralization:** AutoHub centralizes information about various workshops, making it convenient for users to discover and interact with different automotive service providers.

- **Spring Boot Framework:** The application is developed using the Spring Boot framework, providing a robust and scalable backend infrastructure.

- **HTML5, CSS, and Thymeleaf:** The frontend is crafted using modern web technologies, ensuring a responsive and user-friendly interface.

- **Google Maps API Integration:** AutoHub utilizes the Google Maps API for location detection, enhancing the user experience by providing accurate geographical information.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Maven
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Installation

1. Clone the repository:
    git clone https://github.com/your-username/AutoHub.git
2. Navigate to the project directory:
    cd AutoHub
3. Build the project using Maven:
    mvn clean install
4. Run the application:
    java -jar target/autohub.jar

The application will be accessible at http://localhost:8080.

#### General features 
Implemented Features
- `make appointments`
- `search based on services needed`
- `search based on parts needed`
- `appointment history`
- `show nearest workshops`
- `get realtime location using Google API`
- `real time navigation to the workshop from the user`
- `workshops can maintain a detailed service history`

Future planned Features
- `home delivery service`
- `emergency service`
- `review and rating`


## Demonstration

#### Sign up as user 
https://user-images.githubusercontent.com/61306531/231840968-67db3a8c-6404-473a-8bae-50eb2d5710cc.mp4

#### Sign up as workshop
**The workshop can set their location in 2 ways:**
 1. `Automatically using real time location`
 2. `Manually set by the workshop admin`

https://user-images.githubusercontent.com/61306531/231844857-9b9f0357-eb48-4d05-b093-88f86bac65f3.mp4

#### Adding parts and services by workshop

https://user-images.githubusercontent.com/61306531/231848085-f7c09870-be47-42b1-84ef-a78f3e536825.mp4

#### Creating Appointments
1. **From the listed nearby workshops appointments can be made**
2. They can see their real time location on the map
3. Real time route(cycling,driving etc) can be seen of the nearest workshop

https://user-images.githubusercontent.com/61306531/231859446-bacfd143-d728-43e8-9111-df7af3133794.mp4


#### Accepting appointments

Marking the appointment as completed which was done in the previous portion. The starting and the ending time was updated here.

https://user-images.githubusercontent.com/61306531/231865503-c034544a-a1ea-4ec6-9ee8-05fc2fd4a90f.mp4


## Usage

- Visit the homepage to explore available workshops.
- Interact with the map feature powered by Google Maps for location-based information.
- Enjoy a seamless experience in discovering and connecting with automotive service providers.
- Make sure to allow location access for accurate workshop suggestions based on the user's location.


## Contributing

Contributions are welcome! If you have any ideas, enhancements, or bug fixes, please open an issue or submit a pull request.
