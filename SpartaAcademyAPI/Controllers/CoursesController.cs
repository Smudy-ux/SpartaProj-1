using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.AspNetCore.Mvc.Routing;
using Microsoft.EntityFrameworkCore;
using SpartaAcademyAPI.Data;
using SpartaAcademyAPI.Data.DTO;
using SpartaAcademyAPI.Models;
using SpartaAcademyAPI.Services;

namespace SpartaAcademyAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CoursesController : ControllerBase
    {
        private ISpartaAcademyService<Course> _spartanService;
        private readonly IMapper _mapper;

        public CoursesController(ISpartaAcademyService<Course> spartanService, IMapper mapper)
        {
            _spartanService = spartanService;
            _mapper = mapper;
        }

        [HttpGet(Name = "GetCourses")]
        public async Task<ActionResult<IEnumerable<CourseDTO>>> GetCourses()
        {

            return _spartanService is null ? Problem() 
                    : _mapper.Map<List<CourseDTO>>(await _spartanService.GetAllAsync());

        }

        [HttpGet("{id}", Name = "GetCourse")]
        public async Task<ActionResult<CourseDTO>> GetCourse(int id)
        {
            return _spartanService is null ? Problem()
                     : _mapper.Map<CourseDTO>(await _spartanService.GetAsync(id));
        }
    }
}
