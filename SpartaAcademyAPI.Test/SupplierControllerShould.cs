

namespace SpartaAcademyAPI.Test
{
    public class SupplierControllerShould
    {
        [Category("Happy Path")]
        [Category("GetSpartans")]
        [Test]
        public void GetSpartans_WhenThereAreSpartans_ReturnsListOfSpartans()
        {
            var mockService = Mock.Of<ISpartaAcademyService<Spartan>>();
            var mockMapper = Mock.Of<IMapper>();
            List<Spartan> spartans = new List<Spartan>();
            List<SpartanDTO> spartansDto = new List<SpartanDTO>();
            Mock
                .Get(mockService)
                .Setup(sc => sc.GetAllAsync().Result)
                .Returns(spartans);
            Mock
                .Get(mockMapper)
                .Setup(mm => mm.Map<List<SpartanDTO>>(spartans))
                .Returns(spartansDto);

            var sut = new SpartansController(mockService, mockMapper);
            var result = sut.GetSpartans().Result.Value;
            Assert.That(result, Is.InstanceOf<IEnumerable<SpartanDTO>>());
        }


    }
}