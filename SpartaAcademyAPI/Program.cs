using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Routing;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using SpartaAcademyAPI;
using SpartaAcademyAPI.Controllers.Utils;
using SpartaAcademyAPI.Data;
using SpartaAcademyAPI.Data.Repositories;
using SpartaAcademyAPI.Models;
using SpartaAcademyAPI.Services;
using System.Text;

var builder = WebApplication.CreateBuilder(args);
builder.Logging.ClearProviders();
builder.Logging.AddConsole();
// Required for LinkResolver
builder.Services.AddHttpContextAccessor();
builder.Services.AddControllers();
builder.Services.AddDbContext<SpartaAcademyContext>();
builder.Services.AddEndpointsApiExplorer();

// Use the extension method to add JWT authentication
builder.Services.AddJwtAuthentication(builder.Configuration);
// Add Swagger for API documentation
builder.Services.ConfigureSwagger();

builder.Services.AddDbContext<SpartaAcademyContext>();
builder.Services.AddAutoMapper(typeof(AutoMapperProfile));

// Register SpartaAcademyRepository<T> as the implementation for ISpartaAcademyRepository<T>
// This line registers the generic repository implementation for all types T
builder.Services.AddScoped(
    typeof(ISpartaAcademyRepository<>),
    typeof(SpartaAcademyRepository<>));
// Register SpartanRepository as the implementation for ISpartaAcademyRepository<Spartan>
builder.Services.AddScoped<ISpartaAcademyRepository<Spartan>, SpartanRepository>();
// Register CourseRepository as the implementation for ISpartaAcademyRepository<Course>
builder.Services.AddScoped<ISpartaAcademyRepository<Course>, CourseRepository>();
// Register SpartaAcademyService<Spartan> as the implementation for ISpartaAcademyService<Spartan>
builder.Services.AddScoped<ISpartaAcademyService<Spartan>, SpartaAcademyService<Spartan>>();
// Register SpartaAcademyService<Course> as the implementation for ISpartaAcademyService<Course>
builder.Services.AddScoped<ISpartaAcademyService<Course>, SpartaAcademyService<Course>>();
builder.Services.AddTransient<LinksResolver>();
builder.Services.AddControllers()
    .AddNewtonsoftJson(
    opt => opt
        .SerializerSettings
        .ReferenceLoopHandling = Newtonsoft.Json.ReferenceLoopHandling.Ignore);

var app = builder.Build();

// Seed data if in development environment
//if (app.Environment.IsDevelopment())
//{
using (var scope = app.Services.CreateScope())
{
    SeedData.Initialise(scope.ServiceProvider);
}
//}

// Use Swagger for API documentation
app.UseSwagger();
app.UseSwaggerUI(c =>
{
    c.SwaggerEndpoint("/swagger/v1/swagger.json", "My API V1");

    // Enable OAuth2.0
    c.OAuthUsePkce();
});

app.UseHttpsRedirection();
app.UseRouting();

app.UseAuthentication(); 

app.UseAuthorization();
app.UseEndpoints(endpoints =>
{
    endpoints.MapControllers();
    endpoints.MapGet("/", context =>
    {
        context.Response.Redirect("/swagger");
        return Task.CompletedTask;
    });
});

app.Run();