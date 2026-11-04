using Microsoft.EntityFrameworkCore;

namespace SpartaAcademyAPI.Data.Repositories
{
    public interface ISpartaAcademyRepository<T>
    {
        bool IsNull { get; }
        Task<IEnumerable<T>> GetAllAsync();
        Task<T?> FindAsync(int id);
        Task Add(T entity);
        Task AddRange(IEnumerable<T> entities);
        void Update(T entity);
        void Remove(T entity);
        Task SaveAsync();

    }
}
