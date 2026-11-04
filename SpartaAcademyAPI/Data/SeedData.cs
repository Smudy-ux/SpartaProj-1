using Microsoft.EntityFrameworkCore;
using SpartaAcademyAPI.Models;

namespace SpartaAcademyAPI.Data
{
    public class SeedData
    {
        public static void Initialise(IServiceProvider serviceProvider)
        {
            var context = serviceProvider.GetRequiredService<SpartaAcademyContext>();
            if (context.Courses.Any() || context.Spartans.Any() || context.Streams.Any())
            {
                context.Courses.RemoveRange(context.Courses);
                context.Spartans.RemoveRange(context.Spartans);
                context.Streams.RemoveRange(context.Streams);
                context.SaveChanges();
            }

            var streams = new List<Models.Stream>
            {
                new Models.Stream { Name = "C# Dev" },
                new Models.Stream { Name = "C# Test" },
                new Models.Stream { Name = "Java Dev" },
                new Models.Stream { Name = "Jave Test" },
                new Models.Stream { Name = "Java Test" },
                new Models.Stream { Name = "DevOps" },
                new Models.Stream { Name = "Data" },
                new Models.Stream { Name = "Business" }
            };
            context.Streams.AddRange(streams);

            var courses = new List<Course>
            {
                new Course { Name = "TECH 300", Stream = streams[0], Trainer = "Phil Windridge", StartDate = new DateTime(2023, 03, 01), EndDate = new DateTime(2023, 05, 01) },
                new Course { Name = "TECH 301", Stream =  streams[2], Trainer = "Catherine French", StartDate = new DateTime(2023, 04, 01), EndDate = new DateTime(2023, 06, 01) },
                new Course { Name = "TECH 302", Stream =  streams[1], Trainer = "Nish Mandal", StartDate = new DateTime(2023, 05, 01), EndDate = new DateTime(2023, 07, 01) },
                new Course { Name = "TECH 303", Stream =  streams[5], Trainer = "Abdul Shahrukh Khan", StartDate = new DateTime(2023, 06, 01), EndDate = new DateTime(2023, 08, 01) },
                new Course { Name = "DATA 304", Stream =  streams[6], Trainer = "Paula Savaglia", StartDate = new DateTime(2023, 07, 01), EndDate = new DateTime(2023, 09, 01) }
            };
            context.Courses.AddRange(courses);

            var spartans = new List<Spartan>
            {
                new Spartan { FirstName = "Sparty", LastName = "McFly", University = "University of Rome", Degree = "Time Travel", Course = courses[0] },
                new Spartan { FirstName = "John", LastName = "Lennon", University = "Liverpool Hope University", Degree = "Songwriting and Composition", Course = courses[1] },
                new Spartan { FirstName = "Paul", LastName = "McCartney", University = "Liverpool Institute for Performing Arts", Degree = "Music Production and Performance", Course = courses[2] },
                new Spartan { FirstName = "Ringo", LastName = "Starr", University = "University of Liverpool", Degree = "Percussion Studies", Course = courses[3]},
                new Spartan { FirstName = "George", LastName = "Harrison", University = "Liverpool John Moore Unis", Degree = "Music Theory", Course = courses[0] },
                new Spartan { FirstName = "Jay", LastName = "Z", University = "New York University", Degree = "Music Business and Enterpreneurship", Course = courses[1]},
                new Spartan { FirstName = "Ravi", LastName = "Shankar", University = "UCLA", Degree = "Classical Music", Course = courses[2] },
                new Spartan { FirstName = "Nina", LastName = "Simone", University = "University of North Carolina", Degree = "Classics", Course = courses[3]},
                new Spartan { FirstName = "Beyonce", LastName = "Knowles", University = "University of Texas", Degree = "Jazz", Course = courses[0] },
                new Spartan { FirstName = "Keke", LastName = "Okereke", University = "Kings College, London", Degree = "English Literature", Course = courses[1] },
                new Spartan { FirstName = "Taylor", LastName = "Swift", University = "University of Pennsylvania", Degree = "Folklore", Course = courses[2] },
                new Spartan { FirstName = "Lou", LastName = "Reed", University = "Sycrause University", Degree = "Poetry", Course = courses[3]},
                new Spartan { FirstName = "Dua", LastName = "Lipa", University = "Sylvia Young Theatre School", Degree = "New Rules", Course = courses[0]},
                new Spartan { FirstName = "Joan", LastName = "Clarke", University = "University of Camrbridge", Degree = "Mathematics", Course = courses[1] },
                new Spartan { FirstName = "Alan", LastName = "Turing", University = "University of Camrbridge", Degree = "Mathematics", Course = courses[2] },
                new Spartan { FirstName = "Jimi", LastName = "Hendrix", University = "Riff University", Degree = "Purple Haze", Course = courses[3] },
                new Spartan { FirstName = "Ada", LastName = "Lovelace", University = "University of Turin", Degree = "Mathematics", Course = courses[0] },
                new Spartan { FirstName = "Marie", LastName = "Curie", University = "Flying University", Degree = "Radioactivity", Course = courses[1]},
                new Spartan { FirstName = "Stewart", LastName = "Lee", University = "University of Oxford", Degree = "English", Course = courses[2]},
                new Spartan { FirstName = "Charles", LastName = "Darwin", University = "University of Oxford", Degree = "Zoology", Course = courses[3]},
                new Spartan { FirstName = "Rosalind", LastName = "Franklin", University = "University of Cambridge", Degree = "Natural Science",  Course = courses[0] },
                new Spartan { FirstName = "Barbara", LastName = "McClintock", University = "Cornell University", Degree = "Botany", Course = courses[1]},
                new Spartan { FirstName = "Tony", LastName = "Hawks", University = "University of Skate", Degree = "Music", Course = courses[1] },
                new Spartan { FirstName = "Malala", LastName = "Yousafzai", University = "University of Oxford", Degree = "PPE",  Course = courses[2] },
                new Spartan { FirstName = "Eddie", LastName = "Izzard", University = "University of Sheffield", Degree = "Drama", Course = courses[3]}, 
                new Spartan { FirstName = "Wonder", LastName = "Woman", University = "Staffordshire University", Degree = "Law", Course = courses[0]},
                new Spartan { FirstName = "Jack", LastName = "Black", University = "School of Rock", Degree = "Music",  Course = courses[1]},
                new Spartan { FirstName = "Bill", LastName = "Withers", University = "University of Birmingham", Degree = "Biology", Course = courses[2] },
                new Spartan { FirstName = "Super", LastName = "Man", University = "N/A", Degree = "N/A", Course = courses[3] },
                
                new Spartan { FirstName = "Tom", LastName = "Hanks", University = "University of Washington", Degree = "Law",  Course = courses[4] },
                new Spartan { FirstName = "Russell", LastName = "Howard", University = "University of Bristol", Degree = "Drama", Course = courses[4]},
                new Spartan { FirstName = "Liam", LastName = "Gallagher", University = "Manchester Metropolitan University", Degree = "Music Data", Course = courses[4]},
                new Spartan { FirstName = "Noel", LastName = "Gallagher", University = "University of Manchester", Degree = "The Beatles",  Course = courses[4]}
            };

            context.Spartans.AddRange(spartans);

            context.SaveChanges();
        }
    }
}
