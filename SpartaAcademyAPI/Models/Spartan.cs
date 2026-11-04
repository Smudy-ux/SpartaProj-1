using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SpartaAcademyAPI.Models
{
    public class Spartan 
    {
        public int Id { get; set; }
        [Required]
        [StringLength(50, MinimumLength = 6)]
        public string FirstName { get; set; } = null!;
        [Required]
        [StringLength(50, MinimumLength = 6)]
        public string LastName { get; set; } = null!;
        public string? University { get; set; } = string.Empty;
        public string? Degree { get; set; } = string.Empty;

        public Course Course { get; set; } = null!;
        [ForeignKey("Course")]
        public int? CourseId { get; set; } 
        public bool Graduated { get; set; }

    }
}
