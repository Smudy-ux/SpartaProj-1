using System.ComponentModel.DataAnnotations.Schema;

namespace SpartaAcademyAPI.Models
{
    public class Course 
    {
        public Course()
        {
            Spartans = new List<Spartan>();
        }
        public int Id { get; set; }
        public string Name { get; set; }
        public Stream Stream { get; set; } = null!;
        [ForeignKey("Stream")]
        public int StreamId { get; set; }

        //make into Trainer class
        public string Trainer { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public List<Spartan> Spartans { get; set; }

    }
}
