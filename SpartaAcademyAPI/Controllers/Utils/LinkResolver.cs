using AutoMapper;
using SpartaAcademyAPI.Data.DTO;
using SpartaAcademyAPI.Models;

namespace SpartaAcademyAPI.Controllers.Utils
{
    // Use automapper profile to map Links using LinksResolver to DTOs (To do: Make generic)
    public class LinksResolver : IValueResolver<Spartan, SpartanDTO, List<Link>>, IValueResolver<Course, CourseDTO, List<Link>>
    {
        private readonly IHttpContextAccessor _httpContextAccessor;
        private readonly LinkGenerator _linkGenerator;

        public LinksResolver(IHttpContextAccessor httpContextAccessor, LinkGenerator linkGenerator)
        {
            _httpContextAccessor = httpContextAccessor;
            _linkGenerator = linkGenerator;
        }

        public List<Link> Resolve(Spartan source, SpartanDTO destination, List<Link> destMember, ResolutionContext context)
        {
            var httpContext = _httpContextAccessor.HttpContext;
            return new List<Link>
        {
            new Link(_linkGenerator.GetPathByAction(httpContext, action: nameof(SpartansController.GetSpartan), controller: "Spartans", values: new { id = source.Id }), "self", "GET"),
            new Link(_linkGenerator.GetPathByAction(httpContext, action: nameof(SpartansController.PutSpartan), controller: "Spartans", values: new { id = source.Id }), "update_spartan", "PUT"),
            new Link(_linkGenerator.GetPathByAction(httpContext, action: nameof(SpartansController.DeleteSpartan), controller: "Spartans", values: new { id = source.Id }), "delete_spartan", "DELETE"),
            new Link(_linkGenerator.GetPathByAction(httpContext, action: nameof(CoursesController.GetCourse), controller: "Courses", values: new { id = source.CourseId }), "course", "GET")
        };
        }

        public List<Link> Resolve(Course source, CourseDTO destination, List<Link> destMember, ResolutionContext context)
        {
            var httpContext = _httpContextAccessor.HttpContext;
            return new List<Link>
        {
            new Link(_linkGenerator.GetPathByAction(httpContext, action: nameof(CoursesController.GetCourse), controller: "Courses", values: new { id = source.Id }), "self", "GET")
        };
        }
    }
}
