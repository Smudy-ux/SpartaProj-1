###############################
# Stage 1: Build runtime image
###############################

# Use the official ASP.NET Core runtime image from Microsoft Container Registry as the base image
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
# Set the working directory inside the container to /app
WORKDIR /app
# Expose port 8080 for HTTP traffic
EXPOSE 8080
# Expose port 8081 for HTTPS traffic
EXPOSE 8081

###############################
# Stage 2: Build application
###############################

# Use the official .NET Core SDK image from Microsoft Container Registry to build the application
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
# Accept the build configuration (e.g., Debug or Release) as an argument with a default value of Release
ARG BUILD_CONFIGURATION=Release
# Set the working directory inside the container to /src
WORKDIR /src
# Copy the SpartaAcademyAPI.csproj file into the container at /src
COPY ["SpartaAcademyAPI.csproj", "./"]
# Restore the .NET Core packages specified in the SpartaAcademyAPI.csproj file
RUN dotnet restore "./SpartaAcademyAPI.csproj"
# Copy the entire source code into the container at /src
COPY . .
# Build the application in the specified build configuration and output the result to /app/build
RUN dotnet build "SpartaAcademyAPI.csproj" -c $BUILD_CONFIGURATION -o /app/build

###############################
# Stage 3: Publish application
###############################

# Use the build stage as the base for this stage
FROM build AS publish
# Publish the application in the specified build configuration and output the result to /app/publish
RUN dotnet publish "SpartaAcademyAPI.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

###############################
# Stage 4: Final stage
###############################

# Use the base image (runtime image) for this final stage
FROM base AS final
# Set the working directory inside the container to /app
WORKDIR /app
# Copy the published output from the publish stage into the container at /app
COPY --from=publish /app/publish .
# Set the container to run the SpartaAcademyAPI.dll when it starts
ENTRYPOINT ["dotnet", "SpartaAcademyAPI.dll"]