using SpartaAcademyAPI.Data.DTO;
using System.ComponentModel.DataAnnotations;

namespace SpartaAcademyAPI.Models
{
    public class Stream
    {
        public Stream()
        {
            Courses = new List<Course>();
        }
        public int Id { get; set; }
        [Required]
        [StringLength(50, MinimumLength = 6)]
        public string Name { get; set; }

        public List<Course> Courses { get; set; }

        //Name to be enum
        //List of trainers who are able to traine stream
        // What other details?

    }
}

