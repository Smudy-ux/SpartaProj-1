###############################
## Stage 1: Build runtime image
###############################
# Use the ASP.NET runtime image
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
EXPOSE 8080
EXPOSE 8081

###############################
## Stage 2: Build application
###############################
# Use the SDK image to build the application
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
# Copy the csproj file and restore dependencies
COPY ["SpartaAcademyApp/SpartaAcademyAPI.csproj", "./"]
RUN dotnet restore "SpartaAcademyAPI.csproj"
# Copy the entire project and set the working directory
COPY . .
WORKDIR "/src"
# Build the project
RUN dotnet build "SpartaAcademyAPI.csproj" -c $BUILD_CONFIGURATION -o /app/build

###############################
## Stage 3: Publish application
###############################
# Use the build stage to publish the application
FROM build AS publish
ARG BUILD_CONFIGURATION=Release
# Publish the project
RUN dotnet publish "SpartaAcademyAPI.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

###############################
## Stage 4: Final stage
###############################
# Use the base image as the final runtime image
FROM base AS final
WORKDIR /app
# Copy the published output from the publish stage
COPY --from=publish /app/publish .
# Set the entry point for the container
ENTRYPOINT ["dotnet", "SpartaAcademyAPI.dll"]
