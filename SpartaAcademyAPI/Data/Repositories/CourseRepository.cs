using Microsoft.EntityFrameworkCore;
using SpartaAcademyAPI.Models;

namespace SpartaAcademyAPI.Data.Repositories
{
    public class CourseRepository : SpartaAcademyRepository<Course>
    {
        public CourseRepository(SpartaAcademyContext context) : base(context)
        {

        }

        public override async Task<Course?> FindAsync(int id)
        {

            return await _dbSet.Where(s => s.Id == id)
                .Include(s => s.Spartans)
                .Include(s => s.Stream)
                .FirstOrDefaultAsync();
        }

        public override async Task<IEnumerable<Course>> GetAllAsync()
        {
            return await _dbSet
                .Include(s => s.Spartans)
                .Include(s => s.Stream)
                .ToListAsync();
        }
    }
}

