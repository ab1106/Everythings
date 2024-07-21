var currentIndex = 0;
var slidesContainer;
var slides;
var totalSlides;
var slideInterval;

window.addEventListener("load", function() {
    slidesContainer = document.querySelector("#slider");
    slides = document.querySelectorAll(".slide");
    totalSlides = slides.length;

    // Nascondi tutte le immagini tranne la prima
    slides.forEach((slide, index) => {
        if (index !== currentIndex) {
            slide.classList.add("hidden");
        }
    });

    // Start the automatic slider
    startSlider();
});

function startSlider() {
    slideInterval = setInterval(nextSlide, 5000); 
}

function nextSlide() {
    moveSlide((currentIndex + 1) % totalSlides);
}

function previousSlide() {
    moveSlide((currentIndex - 1 + totalSlides) % totalSlides);
}

function moveSlide(newIndex) {
    slides[currentIndex].classList.add("hidden");
    slides[newIndex].classList.remove("hidden");
    currentIndex = newIndex;

    // Restart the interval
    clearInterval(slideInterval);
    startSlider();
}
