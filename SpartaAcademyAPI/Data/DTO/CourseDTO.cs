using System.ComponentModel.DataAnnotations.Schema;

namespace SpartaAcademyAPI.Data.DTO
{
    public class CourseDTO
    {
        public CourseDTO()
        {
            Spartans = new List<string>();
        }
        public int Id { get; set; }
        public string Name { get; set; }
        public string Stream { get; set; }


        //make into Trainer class
        public string Trainer { get; set; }
        public List<string> Spartans { get; set; } 
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public List<Link> Links { get; set; } = new List<Link>();


    }
}
