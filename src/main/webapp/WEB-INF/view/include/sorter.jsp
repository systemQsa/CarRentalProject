<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"
                        href="#">${sessionScope.language['sort_by']}<span class="caret"></span></a>
    <ul class="dropdown-menu" role="menu">
        <li><a href="?sort=byName&action=wantedOrder&order=sortCarsByName" role="button"
               class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_name']}</a>
        </li>
        <li><a href="?sort=byClass&action=wantedOrder&order=sortCarsByClass" role="button"
               class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_class']}</a>
        </li>
        <li>
            <a href="?sort=byBrand&action=wantedOrder&order=sortCarsByBrand" role="button"
               class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_brand']}</a>
        </li>
        <li> <a href="?sort=byPrice&action=wantedOrder&order=sortCarsByRentPrice" role="button"
                class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_price']}</a>
        </li>
    </ul>
</li>
</body>
</html>
