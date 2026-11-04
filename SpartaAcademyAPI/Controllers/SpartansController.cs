using AutoMapper;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SpartaAcademyAPI.Controllers.Utils;
using SpartaAcademyAPI.Data;
using SpartaAcademyAPI.Data.DTO;
using SpartaAcademyAPI.Models;
using SpartaAcademyAPI.Services;

namespace SpartaAcademyAPI.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)] 
    [Route("api/[controller]")]
    [ApiController]
    public class SpartansController : ControllerBase
    {
        private ISpartaAcademyService<Spartan> _spartanService;
        private readonly IMapper _mapper;
        private readonly LinksResolver _linkResolver;


        public SpartansController(ISpartaAcademyService<Spartan> spartanService, IMapper mapper)
        {
            _spartanService = spartanService;
            _mapper = mapper;
        }

        [HttpGet(Name = "GetSpartans")]
        public async Task<ActionResult<IEnumerable<SpartanDTO>>> GetSpartans()
        {
            var spartans = await _spartanService.GetAllAsync();
            if (spartans == null)
            {
                return Problem("Entity set is null");
            }

            var spartanDTOs = _mapper.Map<List<SpartanDTO>>(spartans);


            return spartanDTOs;
        }

        [HttpGet("{id}", Name = "GetSpartan")]
        public async Task<ActionResult<SpartanDTO>> GetSpartan(int id)
        {
            var spartan = await _spartanService.GetAsync(id);
            if (spartan == null)
            {
                return NoContent();
            }

            var spartanDTO = _mapper.Map<SpartanDTO>(spartan);

            return spartanDTO;
        }

        [HttpPost]
        public async Task<ActionResult<SpartanDTO>> PostSpartan([Bind("FirstName, LastName, University, Degree, Course, Stream, Graduated")] Spartan spartan)
        {
            return await _spartanService.CreateAsync(spartan) ?

                 CreatedAtAction(
                    "GetSupplier",
                    new { id = spartan.Id },
                    _mapper.Map<SpartanDTO>(spartan)) :

             Problem("Entity set to null");
        }

        [HttpDelete("{id}", Name = "DeleteSpartan")]
        public async Task<IActionResult> DeleteSpartan(int id)
        {
            return await _spartanService.DeleteAsync(id) ? NoContent() : NotFound();
        }

        [HttpPut("{id}", Name = "PutSpartan")]
        public async Task<IActionResult> PutSpartan(int id, [Bind("FirstName, LastName, University, Degree, Course, Stream, Graduated")] Spartan spartan)
        {
            return (id != spartan.Id) ? BadRequest() :
             (await _spartanService.UpdateAsync(id, spartan)) ? NoContent()
             : NotFound();
        }
    }
}