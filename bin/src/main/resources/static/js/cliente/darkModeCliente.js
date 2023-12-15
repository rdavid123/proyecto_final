/* Client */
const bgBody = document.querySelector('body');
const btnDarkMode = document.querySelector('#btn-dark-mode');
const toogleDM = document.querySelector('.toggle-dark-mode');

let getMode = localStorage.getItem("mode"); //modo

btnDarkMode.addEventListener('click', function() {
	bgBody.classList.toggle('dark-mode');
	if(bgBody.classList.contains("dark-mode")){
        localStorage.setItem("mode", "dark-mode"); //esteblece modo oscuro
    }else{
        localStorage.setItem("mode", "light");//esteblece modo light
    }

	toogleDM.classList.toggle('active');
})

if(getMode && getMode ==="dark-mode"){ //si el modo es dark-mode
    bgBody.classList.toggle("dark-mode"); //aplicamos clase dark-mode
	toogleDM.classList.toggle('active'); //activamos el toggle darkmode
}