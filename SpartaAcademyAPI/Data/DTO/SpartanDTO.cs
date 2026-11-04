using SpartaAcademyAPI.Models;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace SpartaAcademyAPI.Data.DTO
{
    public class SpartanDTO
    {
        public int Id { get; set; }
        [Required]
        [StringLength(50, MinimumLength = 6)]
        public string FirstName { get; set; }
        [Required]
        [StringLength(50, MinimumLength = 6)]
        public string LastName { get; set; }
        public string? University { get; set; } = string.Empty;
        public string? Degree { get; set; } = string.Empty;
        public string Course { get; set; }
        public string Stream { get; set; }
        public bool Graduated { get; set; }

        public List<Link> Links { get; set; } = new List<Link>();

    }
}
