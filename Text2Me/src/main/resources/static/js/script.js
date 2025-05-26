const loginButton = document.querySelector('.login-button');

loginButton.addEventListener('mouseenter', () => {
    loginButton.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.3)';
});

loginButton.addEventListener('mouseleave', () => {
    loginButton.style.boxShadow = '0 5px 10px rgba(0, 0, 0, 0.2)';
});

document.addEventListener('scroll', () => {
    document.body.style.backgroundPosition = `0px ${window.scrollY / 3}px`;
});

const returnButton = document.querySelector('.return-button');

returnButton.addEventListener('mouseenter', () => {
    returnButton.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.3)';
});

returnButton.addEventListener('mouseleave', () => {
    returnButton.style.boxShadow = '0 5px 10px rgba(0, 0, 0, 0.2)';
});

const registerButton = document.querySelector('.register-button');

registerButton.addEventListener('mouseenter', () => {
    returnButton.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.3)';
});

registerButton.addEventListener('mouseleave', () => {
    registerButton.style.boxShadow = '0 5px 10px rgba(0, 0, 0, 0.2)';
});

s



