const apiUrl = "http://localhost:8080"; 

async function loadStreams() {
    try {
        const response = await fetch(`${apiUrl}/streams`);
        const streams = await response.json();

        const streamSelect = document.getElementById("streamSelect");
        streamSelect.innerHTML = ""; 

        streams.forEach(stream => {
            const option = document.createElement("option");
            option.value = stream.id;
            option.textContent = stream.titulo;
            streamSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Error loading streams:", error);
    }
}

async function createPost() {
    const username = document.getElementById("username").value;
    const content = document.getElementById("content").value;
    const streamSelect = document.getElementById("streamSelect");
    const streamTitle = streamSelect.options[streamSelect.selectedIndex].textContent;
    
    console.log(streamTitle);

    if (!username || !content || !streamTitle) {
        alert("Username, content, and stream are required!");
        return;
    }

    try {
        const response = await fetch(`${apiUrl}/posts`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                contenido: content,
                streamTitle: streamTitle
            })
        });

        if (response.ok) {
            alert("Post created successfully!");
            document.getElementById("content").value = ""; 
            loadPosts(); 
        } else if (response.status === 404) {
            const errorData = await response.json();
            alert(errorData.error);
        } else {
            alert("Error creating new post");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

async function loadPosts() {
    try {
        const response = await fetch(`${apiUrl}/posts`);
        const posts = await response.json();

        const postsContainer = document.getElementById("posts");
        postsContainer.innerHTML = ""; 

        const postsByStream = {};
        posts.forEach(post => {
            const streamTitle = post.streamTitle || "General"; 
            if (!postsByStream[streamTitle]) {
                postsByStream[streamTitle] = [];
            }
            postsByStream[streamTitle].push(post);
        });

        for (const [streamTitle, posts] of Object.entries(postsByStream)) {
            const streamSection = document.createElement("div");
            streamSection.className = "stream-section";
            streamSection.innerHTML = `<h3>${streamTitle}</h3>`;
            
            posts.forEach(post => {
                const postElement = document.createElement("div");
                postElement.className = "post";
                postElement.innerHTML = `<strong>${post.username}</strong>: ${post.contenido}`;
                streamSection.appendChild(postElement);
            });

            postsContainer.appendChild(streamSection);
        }
    } catch (error) {
        console.error("Error loading posts:", error);
    }
}

loadStreams();
loadPosts();
