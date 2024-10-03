document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('propertyForm');
    const searchForm = document.getElementById('searchProperty');
    const deleteButton =document.getElementById('deleteProperty');
    const updateButton =document.getElementById('updateProperty');
    const url = 'http://localhost:8080/v1/arep/property';
    loadProperties();
    form.addEventListener('submit', addProperty);
    searchForm.addEventListener('submit', getProperty);
    deleteButton.addEventListener('click', deleteProperty);
    updateButton.addEventListener('click', updateProperty);
    listProperties = [];

    async function addProperty(event) {
        event.preventDefault();

        const address = document.getElementById('address').value;
        const price = document.getElementById('price').value;
        const size = document.getElementById('size').value;
        const description = document.getElementById('description').value;
        const property = { address, price, size, description };

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(property),
            });
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const result = await response.json();
            console.log('Server response:', result);
            form.reset();
            loadProperties();
            return result;
        } catch (error) {
            alert('ERROR: '+ error);
        }
    }

    async function loadProperties(){
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const properties = await response.json();
            createTable(properties,0,properties.length);
        } catch (error) {
            alert("ERROR: "+ error);
            console.error('ERROR:'+ error);
        }
    }

    async function getProperty(address){
            try {
                const response = await fetch(`${url}/${id}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }
                const properties = await response.json();
                createTable(properties,0,properties.length);
            } catch (error) {
                alert("ERROR: "+ error);
            }
        }


    async function updateProperty(address) {
        try {
            const newPrice = prompt("Nuevo Precio:");
            const newSize = prompt("Nuevo Tamaño:");
            const newDescription = prompt("Nueva descripcion:");

            if (newPrice && newSize && newDescription) {
                const updatedProperty = {
                    address: address,
                    price: newPrice,
                    size: newSize,
                    description: newDescription
                };

                const response = await fetch(`${url}/${address}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(updatedProperty),
                });

                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }

                const result = await response.json();
                console.log('Server response:', result);
                loadProperties();
                return result;
            } else {
                alert("Todos los campos son obligatorios.");
            }
        } catch (error) {
            alert("ERROR: " + error);
        }
    }

    async function deleteProperty(id) {
        try {
            if (confirm("¿Estás seguro de que deseas borrar esta propiedad?")) {
                const response = await fetch(`${url}/${id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if (!response.ok) {
                    alert("ERROR: " + response.status);
                    throw new Error(`Error: ${response.status}`);
                }
                loadProperties();
            }
        } catch (error) {
            alert("ERROR: " + error);
        }
    }

    function createTable(courseList, start, limit) {
        let table = document.getElementById("listTable").getElementsByTagName('tbody')[0];
        table.innerHTML = ''; // Limpiar la tabla
        for (let i = start; i < limit; i++) {
            let course = courseList[i];
            let row = table.insertRow();
            let cell1 = row.insertCell(0);
            let cell2 = row.insertCell(1);
            let cell3 = row.insertCell(2);
            let cell4 = row.insertCell(3);
            let cell5 = row.insertCell(4);
            cell1.innerHTML = course.address;
            cell2.innerHTML = course.price;
            cell3.innerHTML = course.size;
            cell4.innerHTML = course.description;
            cell5.innerHTML = `
                <div class="mt-3">
                    <button class="deleteProperty btn btn-danger">Eliminar</button>
                    <button class="updateProperty btn btn-warning">Actualizar</button>
                </div>
            `;
            let deleteButton = cell5.querySelector('.deleteProperty');
            let updateButton = cell5.querySelector('.updateProperty');
            deleteButton.addEventListener('click', function() {
                deleteProperty(course.id);
            });

            updateButton.addEventListener('click', function() {
                console.log('Update:', course.address);
                updateProperty(course.address);
            });
        }
    }
});
