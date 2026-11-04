using Microsoft.EntityFrameworkCore;
using SpartaAcademyAPI.Models;

namespace SpartaAcademyAPI.Data.Repositories
{
    public class SpartanRepository : SpartaAcademyRepository<Spartan>
    {
        public SpartanRepository(SpartaAcademyContext context) : base(context)
        {

        }

        public override async Task<Spartan?> FindAsync(int id)
        {

            return await _dbSet.Where(s => s.Id == id)
                .Include(s => s.Course)
                .ThenInclude(c => c.Stream)
                .FirstOrDefaultAsync();
        }

        public override async Task<IEnumerable<Spartan>> GetAllAsync()
        {
            return await _dbSet.Include(s => s.Course)
                .ThenInclude(c => c.Stream)
                .ToListAsync();
        }
    }
}
