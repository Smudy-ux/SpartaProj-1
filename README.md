<img src="https://boolerang.co.uk/wp-content/uploads/job-manager-uploads/company_logo/2018/04/SG-Logo-Black.png" alt="Sparta Logo" width="200"/>

---

# Sparta Global ASP.NET API

You can view the swagger documentation for the API [here](https://spartaacademyapi20240530152521.azurewebsites.net/)

If you intend to run a local image or container, follow [these steps](#Containersing-the api)

The **SpartaAcademyAPI** allows you to:

* **View all courses**: `/api/courses` (GET)
* **View a specific course**: `/api/courses/{courseId}` (GET)
* **View all Spartans**: `/api/spartans` (GET)
* **View a specific Spartan**: `/api/spartans/{spartanId}` (GET)
* **Create a new Spartan**: `/api/spartans` (POST)
* **Update an existing Spartan**: `/api/spartans/{spartanId}` (PUT)
* **Delete an existing Spartan**: `/api/spartans/{spartanId}` (DELETE)

Details about the end points should appear in the swagger documentation

## Obtain Bearer Token

Ensure that the Bearer token is included in the Authorisation header for endpoints that require authentication. 

To retrieve a bearer token, make a POST request to the end point `/api/auth/login` with the following request body:

```json
{
  "username": "sparta",
  "password": "global"
}
```

Status code response should be **200**

Example response body:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoic3BhcnRhIiwiZXhwIjoxNzE5MzEzNTg4LCJpc3MiOiJZb3VySXNzdWVyIiwiYXVkIjoiWW91cklzc3VlciJ9.j3nJgk7KqmdASKPaUxu7AEo7RtlRj-lZf5NJKrTTLyw"
}
```

Example response headers:

```
content-length: 261 
content-type: application/json; charset=utf-8 
date: Tue,25 Jun 2024 10:36:27 GMT 
server: Kestrel 
```



## Containerising the API



Alternatively, you should be able to containerize the app and run it locally using Docker. To do so, follow the steps below:



* Download [Docker Desktop](https://www.docker.com/products/docker-desktop/), create an account, and complete the installation steps.
  
  

* Download the `SpartaAcademyApp.zip` file.
  
  

* Open your command-line interface (CLI) and navigate to the **SpartaAcademyAPI** directory.
  
  
  
  > **Note:** The Dockerfile is located in this directory. Review the Dockerfile to understand the  actions performed when executing the next command.
  
  

* Execute the following command in the CLI to build the Docker image tagged as `spartaacademyapi`:
  
  

```powershell
docker build -t spartaacademyapi .
```

> 
> 
> This command reads instructions from the Dockerfile and builds an image for the SpartaAcademyAPI application.



* Start a Docker container with the built image using the following command:
  
  

```cmd
docker run -d -p 8080:8080 -p 8081:8081 spartaacademyapi
```



> This command runs the container in detached mode (`-d`), mapping port 8080 on your local machine to port 8080 on the container, and port 8081 on your local machine to port 8081 on the container.



* Open your web browser and go to [http://localhost:8080/](http://localhost:8080/) to access the locally hosted SpartaAcademyAPI.
  
  

* If you encounter any issues during setup:
  
  * Open Docker Desktop.
  * Navigate to the Builds section in the left-side panel.
  * Open the build for SpartaAcademyAPI.
  * Review any error logs displayed.
  * Report any encountered errors to your Academy Trainer for assistance.
    
    

### Stopping the container

To stop a Docker container that is running an image, you can follow these steps:

1. **Identify the Container ID or Name**: First, you need to know the ID or the name of the container running your image. You can find this by running:
   
   

```cmd
docker ps
```

> This command lists all running containers along with their IDs, names, and other details.



2. **Stop the Container**: Once you have identified the container ID or name, use the following command to stop it:
   
   

```cmd
docker stop <container_id_or_name>
```



   Replace `<container_id_or_name>` with the actual ID or name of your container.



   For example:



```cmd
docker stop abcdef123456
```



   Or if your container has a name:



```cmd
docker stop my_container
```



   This command sends a stop signal to the container, causing it to gracefully shut down.



3. **Verify**: To ensure the container has stopped, you can run `docker ps` again. If your container no longer appears in the list of running containers, it has successfully stopped.
   
   

If you want to remove the stopped container (cleanup), you can use `docker rm <container_id_or_name>`. This removes the container permanently, freeing up resources.

Stopping a Docker container halts its execution but preserves its state and configuration. If you need to start it again later, you can use `docker start <container_id_or_name>` to resume it.
