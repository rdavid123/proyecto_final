/* Admin */
const bodyAdmin = document.getElementById('body-admin')
const sidebar = document.getElementById('sidebar-admin');
const toggle = document.querySelector('.toggle');
const searchbtn = document.querySelector('.search-box');
const modeSwitch = document.querySelector('.toggle-switch');
const modeText = document.querySelector('.mode-text');

/* MODE */
let getMode = localStorage.getItem("modeAdmin");
if(getMode && getMode ==="dark"){
    bodyAdmin.classList.toggle("dark");
}
/* STATUS */
let getStatus = localStorage.getItem("statusAdmin");
if(getStatus && getStatus ==="close"){
    sidebar.classList.toggle("close");
}

/* EVENTS */
toggle.addEventListener('click',()=>{
	sidebar.classList.toggle('close');
	/* set status */ 
	if(sidebar.classList.contains("close")){
        localStorage.setItem("statusAdmin", "close");
    }else{
        localStorage.setItem("statusAdmin", "open");
    }
})

searchbtn.addEventListener('click',()=>{
	sidebar.classList.remove('close');
})

modeSwitch.addEventListener('click',()=>{
	bodyAdmin.classList.toggle('dark');

	/* set mode */ 
	if(bodyAdmin.classList.contains("dark")){
        localStorage.setItem("modeAdmin", "dark");
    }else{
        localStorage.setItem("modeAdmin", "light");
    }

	if(bodyAdmin.classList.contains('dark')){
		modeText.innerHTML = "Light Mode";
	}else{
		modeText.innerHTML = "Dark Mode";
	}
})