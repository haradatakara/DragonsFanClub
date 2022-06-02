const btn = document.querySelectorAll('.twitter-heart')
let img = ["/img/twitter/heart_gry.jpeg", "/img/twitter/heart_blue.jpeg"]
const likeNum = document.querySelectorAll('.like-num');
let nums = new Array();

let listItems = [];
const storage = localStorage;


btn.forEach((b, i) => b.addEventListener('click', () => {
	if (getComputedStyle(b).background === 'rgba(0, 0, 0, 0) url("http://localhost:8080/img/twitter/heart_gry.jpeg") no-repeat scroll 0% 50% / auto padding-box border-box') {
		b.style.background = 'url(' + img[1] + ') left no-repeat';
	} else {
		b.style.background = 'url(' + img[0] + ') left no-repeat';
	}
}))