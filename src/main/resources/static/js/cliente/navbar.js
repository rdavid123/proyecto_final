/* Navbar cliente*/

let menu = document.querySelector("#menu-icon");
let navbar = document.querySelector(".navbar");

menu.onclick = () => {
	menu.classList.toggle('bx-x');
	navbar.classList.toggle('open');
}

const menuUser = document.querySelector('#menu-user');
const dropdownMenu = document.querySelector('.dropdown-menu');
menuUser.addEventListener('click', function() {
	dropdownMenu.classList.toggle('active');
})

