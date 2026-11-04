namespace SpartaAcademyAPI.Services
{
    public interface ISpartaAcademyService<T>
    {
        Task<bool> CreateAsync(T entity);
        Task<bool> UpdateAsync(int id, T entity);
        Task<bool> DeleteAsync(int id);
        Task<IEnumerable<T>?> GetAllAsync();
        Task<T?> GetAsync(int id);
    }
}
