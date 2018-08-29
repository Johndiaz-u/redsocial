function valido(form)
{
	if (form.username.value == "") //comprueba si el campo nombre esta vacío
	{
		alert ("No puedes dejarlo vacío");
		form.username.focus();   //posicionarse en el campo vacío
		return false;
	}
	else if (form.password.value == "") //comprueba password esta vacio 
	{
		alert ("No puedes dejarlo vacío");
		form.password.focus();   //posicionarse en el campo vacío
		return false;
	}
	else if (form.password.value == "") //comprueba password esta vacio 
	{
		alert ("No puedes dejarlo vacío");
		form.password.focus();   //posicionarse en el campo vacío
		return false;
	}
	else if (form.password.value == "") //comprueba password esta vacio 
	{
		alert ("No puedes dejarlo vacío");
		form.password.focus();   //posicionarse en el campo vacío
		return false;
	}
	else if (form.email.value == "") //comprueba email esta vacio 
	{
		alert ("No puedes dejarlo vacío");
		form.email.focus();   //posicionarse en el campo vacío
		return false;
	}
	else if (form.password2.value == "") //comprueba email esta vacio 
	{
		alert ("No puedes dejarlo vacío");
		form.password2.focus();   //posicionarse en el campo vacío
		return false;
	}
	else
		return true;
}

//onClick="valido(this.form)"
