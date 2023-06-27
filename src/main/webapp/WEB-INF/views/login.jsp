<%@include file="libs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login page</title>
</head>
<body>
<div class="d-flex">
        <div class="form-check form-switch ms-auto mt-3 me-3">
          <label class="form-check-label ms-3" for="lightSwitch">
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-brightness-high" viewBox="0 0 16 16">
              <path d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6zm0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"></path>
            </svg>
          </label>
          <input class="form-check-input" type="checkbox" id="lightSwitch">
        </div>
      </div>
	<div class="container text-center pt-5">
		<div class="col">
			<div class="row"><p class="h1">Login</p></div>
		</div>
	</div>
	<div class="container p-5 my-5 overflow-hidden text-center">
		<form method="post" action="${pageContext.request.contextPath}/login">
			<div class="row gy-5 align-items-center">
				<div class="col-6">
					<div class="p-3">Username:</div>
				</div>
				<div class="col-6">
					<div class="p-3">
						<input class="form-control" type="text"
							placeholder="Default input" aria-label="default input example"
							name="username" />
					</div>
				</div>
				<div class="col-6">
					<div class="p-3">Password:</div>
				</div>
				<div class="col-6">
					<div class="p-3">
						<input class="form-control" type="text"
							placeholder="Default input" aria-label="default input example"
							name="password" />
					</div>
				</div>
				<div class="col-12">
					<div class="form-check">
						<input class="form-check-input" type="checkbox"
							id="flexCheckDefault" name="remember-me"> <label
							class="form-check-label" for="flexCheckDefault">Remember
							me</label>
					</div>
				</div>
				<div class="col-12">
					<button type="submit" class="btn btn-primary mb-3">
						Confirm identity</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

<script type="text/javascript">
/**
 *  Light Switch @version v0.1.2
 *  @author han109k
 */

(function () {
  let lightSwitch = document.getElementById("lightSwitch");
  if (lightSwitch) {
    darkMode();
    lightSwitch.addEventListener("change", () => {
      lightMode();
    });

    /**
     * @function darkmode
     * @summary: firstly, checks if browser local storage has 'lightSwitch' key
     * if key has 'dark' value then changes the theme to 'dark mode'
     * Basically, replaces/toggles every CSS class that has '-light' class with '-dark'
     */
    function darkMode() {
      let isSelected =
        localStorage.getItem("lightSwitch") !== null &&
        localStorage.getItem("lightSwitch") === "dark";

      if (isSelected) {
        document.querySelectorAll(".bg-light").forEach((element) => {
          element.className = element.className.replace(/-light/g, "-dark");
        });

        document.body.classList.add("bg-dark");

        if (document.body.classList.contains("text-dark")) {
          document.body.classList.replace("text-dark", "text-light");
        } else {
          document.body.classList.add("text-light");
        }
        
        // set light switch input to true
        lightSwitch.checked = true;
      }
    }

    /**
     * @function lightmode
     * @summary: check whether the switch is on (checked) or not.
     * If the switch is on then set 'lightSwitch' local storage key and call @function darkmode
     * If the switch is off then it is light mode so, switch the theme and
     *  remove 'lightSwitch' key from local storage
     */
    function lightMode() {
      if (lightSwitch.checked) {
        localStorage.setItem("lightSwitch", "dark");
        darkMode();
      } else {
        document.querySelectorAll(".bg-dark").forEach((element) => {
          element.className = element.className.replace(/-dark/g, "-light");
        });
        document.body.classList.replace("text-light", "text-dark");
        localStorage.removeItem("lightSwitch");
      }
    }
  }
  
})();


</script>

