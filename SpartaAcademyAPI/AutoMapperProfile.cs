using AutoMapper;
using SpartaAcademyAPI.Controllers.Utils;
using SpartaAcademyAPI.Data.DTO;
using SpartaAcademyAPI.Models;

namespace SpartaAcademyAPI
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<Spartan, SpartanDTO>()
                .ForMember(dest => dest.Course, opt => opt.MapFrom(src => src.Course.Name))
                .ForMember(dest => dest.Stream, opt => opt.MapFrom(src => src.Course.Stream.Name))
                .ForMember(dest => dest.Links, opt => opt.MapFrom<LinksResolver>())
                .ReverseMap();

            CreateMap<Course, CourseDTO>()
                .ForMember(dest => dest.Spartans, opt => opt.MapFrom(src => src.Spartans.Select(s => $"{s.FirstName} {s.LastName}")))
                .ForMember(dest => dest.Stream, opt => opt.MapFrom(src => src.Stream.Name))
                .ForMember(dest => dest.Links, opt => opt.MapFrom<LinksResolver>())
                .ReverseMap()
                .ForMember(dest => dest.Spartans, opt => opt.Ignore()); // Ignore mapping the list of Spartans back to the Course entity

            CreateMap<Models.Stream, StreamDTO>().ReverseMap();
        }
    }
}