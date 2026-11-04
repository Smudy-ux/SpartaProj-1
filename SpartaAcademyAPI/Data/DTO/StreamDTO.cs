using SpartaAcademyAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace SpartaAcademyAPI.Data.DTO
{
    public class StreamDTO
    {
        public StreamDTO()
        {
            Courses = new List<CourseDTO>();
        }
        public int Id { get; set; }
        [Required]
        [StringLength(50, MinimumLength = 6)]
        public string Name { get; set; }

        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }

        public List<CourseDTO> Courses { get; set; }
    }
}
