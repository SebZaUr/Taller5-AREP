const form = document.getElementById('propertyForm');
const propertyList = document.getElementById('propertyList');

form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const address = document.getElementById('address').value;
    const price = document.getElementById('price').value;
    const size = document.getElementById('size').value;
    const description = document.getElementById('description').value;

    const property = { address, price, size, description };

    // Send POST request to add property
    await fetch('http://your-backend-url/properties', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(property),
    });

    form.reset();
    loadProperties(); // Refresh the property list
});

// Function to load properties
async function loadProperties() {
    const response = await fetch('http://your-backend-url/properties');
    const properties = await response.json();
    displayProperties(properties);
}

// Function to display properties
function displayProperties(properties) {
    propertyList.innerHTML = '';
    properties.forEach(property => {
        const div = document.createElement('div');
        div.className = 'property-item';
        div.innerHTML = `
            <strong>${property.address}</strong><br>
            Price: $${property.price} | Size: ${property.size} sq ft<br>
            Description: ${property.description}<br>
            <button onclick="updateProperty(${property.id})">Update</button>
            <button onclick="deleteProperty(${property.id})">Delete</button>
        `;
        propertyList.appendChild(div);
    });
}

// Update property function
async function updateProperty(id) {
    const updatedProperty = prompt('Enter new address, price, size, description (comma separated):').split(',');
    if (updatedProperty.length < 4) return alert('Invalid input');

    const [address, price, size, description] = updatedProperty;

    await fetch(`http://your-backend-url/properties/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ address, price, size, description }),
    });

    loadProperties(); // Refresh the property list
}

// Delete property function
async function deleteProperty(id) {
    await fetch(`http://your-backend-url/properties/${id}`, {
        method: 'DELETE',
    });

    loadProperties(); // Refresh the property list
}

// Load properties on page load
window.onload = loadProperties;
