using Microsoft.EntityFrameworkCore;

namespace SpartaAcademyAPI.Data.Repositories
{
    public class SpartaAcademyRepository<T> : ISpartaAcademyRepository<T> where T : class
    {
        private readonly SpartaAcademyContext _context;
        protected readonly DbSet<T> _dbSet;
        public SpartaAcademyRepository(SpartaAcademyContext context)
        {
            _context = context;
            _dbSet = context.Set<T>();
        }
        public bool IsNull => _dbSet == null;

        public async Task Add(T entity)
        {
            await _dbSet.AddAsync(entity);
        }

        public async Task AddRange(IEnumerable<T> entities)
        {
            await _dbSet.AddRangeAsync(entities);
        }

        public async virtual Task<T?> FindAsync(int id)
        {
            return await _dbSet.FindAsync(id);
        }

        public async virtual Task<IEnumerable<T>> GetAllAsync()
        {
            return await _dbSet.ToListAsync();
        }

        public void Remove(T entity)
        {
            _dbSet.Remove(entity);
        }

        public async Task SaveAsync()
        {
           await _context.SaveChangesAsync();
        }

        public void Update(T entity)
        {
            _dbSet.Update(entity);
        }

    }
}
