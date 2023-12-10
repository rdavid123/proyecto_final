const btnDropdown = document.querySelector('.avatar-user');
const dropdown = document.querySelector('.dropdown');

btnDropdown.addEventListener('click', function (event) {
    event.stopPropagation();
    dropdown.classList.toggle('active');
});

window.addEventListener('click', function (event) {
    if (!event.target.closest('.dropdown')) {
        dropdown.classList.remove('active');
    }
});
