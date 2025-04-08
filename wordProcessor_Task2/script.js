let documentState = {
  name: "Untitled Document",
  saved: false,
  zoomLevel: 100,
  modified: false,
};

const editor = document.getElementById("editor");
const documentName = document.getElementById("document-name");
const wordCountDisplay = document.getElementById("word-count-display");
const charCountDisplay = document.getElementById("char-count-display");
const zoomLevelDisplay = document.getElementById("zoom-level");

const saveDialog = document.getElementById("save-dialog");
const openDialog = document.getElementById("open-dialog");
const linkDialog = document.getElementById("link-dialog");
const wordCountDialog = document.getElementById("word-count-dialog");
const aboutDialog = document.getElementById("about-dialog");

// Initialize editor
editor.addEventListener("input", () => {
  updateWordCount();
  documentState.modified = true;
  updateDocumentName();
});

//  buttons
document.getElementById("bold").addEventListener("click", () => {
  document.execCommand("bold", false, null);
});

document.getElementById("italic").addEventListener("click", () => {
  document.execCommand("italic", false, null);
});

document.getElementById("underline").addEventListener("click", () => {
  document.execCommand("underline", false, null);
});

document.getElementById("align-left").addEventListener("click", () => {
  document.execCommand("justifyLeft", false, null);
});

document.getElementById("align-center").addEventListener("click", () => {
  document.execCommand("justifyCenter", false, null);
});

document.getElementById("align-right").addEventListener("click", () => {
  document.execCommand("justifyRight", false, null);
});

document.getElementById("align-justify").addEventListener("click", () => {
  document.execCommand("justifyFull", false, null);
});

document.getElementById("bullet-list").addEventListener("click", () => {
  document.execCommand("insertUnorderedList", false, null);
});

document.getElementById("numbered-list").addEventListener("click", () => {
  document.execCommand("insertOrderedList", false, null);
});

// Format selects
document.getElementById("format-block").addEventListener("change", (e) => {
  document.execCommand("formatBlock", false, `<${e.target.value}>`);
});

document.getElementById("font-family").addEventListener("change", (e) => {
  document.execCommand("fontName", false, e.target.value);
});

document.getElementById("font-size").addEventListener("change", (e) => {
  document.execCommand("fontSize", false, e.target.value);
});

// Color pickers
document.getElementById("forecolor").addEventListener("input", (e) => {
  document.execCommand("foreColor", false, e.target.value);
});

document.getElementById("backcolor").addEventListener("input", (e) => {
  document.execCommand("hiliteColor", false, e.target.value);
});

// Menu items
document.getElementById("new-doc").addEventListener("click", () => {
  if (documentState.modified) {
    if (
      confirm("You have unsaved changes. Do you want to create a new document?")
    ) {
      createNewDocument();
    }
  } else {
    createNewDocument();
  }
});

document.getElementById("save-doc").addEventListener("click", () => {
  saveDialog.classList.remove("hidden");
  document.getElementById("save-document-name").value = documentState.name;
});

document.getElementById("open-doc").addEventListener("click", () => {
  if (documentState.modified) {
    if (
      confirm(
        "You have unsaved changes. Do you want to open a different document?"
      )
    ) {
      openDialog.classList.remove("hidden");
    }
  } else {
    openDialog.classList.remove("hidden");
  }
});

document.getElementById("print-doc").addEventListener("click", () => {
  window.print();
});

document.getElementById("insert-link").addEventListener("click", () => {
  linkDialog.classList.remove("hidden");
});

document.getElementById("word-count").addEventListener("click", () => {
  updateWordCountDialog();
  wordCountDialog.classList.remove("hidden");
});

document.getElementById("about").addEventListener("click", () => {
  aboutDialog.classList.remove("hidden");
});

// Dialog buttons
document.getElementById("cancel-save").addEventListener("click", () => {
  saveDialog.classList.add("hidden");
});

document.getElementById("confirm-save").addEventListener("click", () => {
  saveDocument();
  saveDialog.classList.add("hidden");
});

document.getElementById("cancel-open").addEventListener("click", () => {
  openDialog.classList.add("hidden");
});

document.getElementById("confirm-open").addEventListener("click", () => {
  openDocument();
  openDialog.classList.add("hidden");
});

document.getElementById("cancel-link").addEventListener("click", () => {
  linkDialog.classList.add("hidden");
});

document.getElementById("confirm-link").addEventListener("click", () => {
  insertLink();
  linkDialog.classList.add("hidden");
});

document.getElementById("close-word-count").addEventListener("click", () => {
  wordCountDialog.classList.add("hidden");
});

document.getElementById("close-about").addEventListener("click", () => {
  aboutDialog.classList.add("hidden");
});

// Zoom controls
document.getElementById("zoom-in").addEventListener("click", () => {
  changeZoom(10);
});

document.getElementById("zoom-out").addEventListener("click", () => {
  changeZoom(-10);
});

document.getElementById("zoom-reset").addEventListener("click", () => {
  documentState.zoomLevel = 100;
  updateZoom();
});

// Functions
function createNewDocument() {
  editor.innerHTML = "<p>Start typing your document here...</p>";
  documentState.name = "Untitled Document";
  documentState.modified = false;
  updateDocumentName();
  updateWordCount();
}

function saveDocument() {
  const name =
    document.getElementById("save-document-name").value || "Untitled Document";
  documentState.name = name;
  documentState.modified = false;
  updateDocumentName();

  // Create a blob and download the file
  const content = editor.innerHTML;
  const blob = new Blob([content], { type: "text/html" });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = `${name}.html`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}

function openDocument() {
  const fileInput = document.getElementById("file-input");
  const file = fileInput.files[0];

  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      try {
        editor.innerHTML = e.target.result;
        documentState.name = file.name.replace(/\.[^/.]+$/, "");
        documentState.modified = false;
        updateDocumentName();
        updateWordCount();
      } catch (error) {
        alert("Error opening file: " + error.message);
      }
    };
    reader.readAsText(file);
  }
}

function insertLink() {
  const url = document.getElementById("link-url").value;
  const text = document.getElementById("link-text").value || url;

  if (url) {
    document.execCommand(
      "insertHTML",
      false,
      `<a href="${url}" target="_blank">${text}</a>`
    );
  }
}

function updateWordCount() {
  const text = editor.innerText || "";
  const wordCount = text.trim() ? text.trim().split(/\s+/).length : 0;
  const charCount = text.length;

  wordCountDisplay.textContent = `Words: ${wordCount}`;
  charCountDisplay.textContent = `Characters: ${charCount}`;
}

function updateWordCountDialog() {
  const text = editor.innerText || "";
  const wordCount = text.trim() ? text.trim().split(/\s+/).length : 0;
  const charCount = text.length;
  const charNoSpaces = text.replace(/\s+/g, "").length;
  const paragraphCount = (editor.innerHTML.match(/<p[^>]*>/g) || []).length;

  document.getElementById("dialog-word-count").textContent = wordCount;
  document.getElementById("dialog-char-count").textContent = charCount;
  document.getElementById("dialog-char-no-spaces").textContent = charNoSpaces;
  document.getElementById("dialog-paragraph-count").textContent =
    paragraphCount;
}

function updateDocumentName() {
  documentName.textContent =
    documentState.name + (documentState.modified ? "*" : "");
}

function changeZoom(amount) {
  documentState.zoomLevel += amount;
  if (documentState.zoomLevel < 10) documentState.zoomLevel = 10;
  if (documentState.zoomLevel > 500) documentState.zoomLevel = 500;
  updateZoom();
}

function updateZoom() {
  editor.style.zoom = `${documentState.zoomLevel}%`;
  zoomLevelDisplay.textContent = `${documentState.zoomLevel}%`;
}

// Fullscreen toggle
document.getElementById("fullscreen").addEventListener("click", () => {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen();
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    }
  }
});

// Add active class to toolbar buttons when their command is active
editor.addEventListener("mouseup", updateToolbarState);
editor.addEventListener("keyup", updateToolbarState);

function updateToolbarState() {
  // Update bold button
  document
    .getElementById("bold")
    .classList.toggle("active", document.queryCommandState("bold"));

  // Update italic button
  document
    .getElementById("italic")
    .classList.toggle("active", document.queryCommandState("italic"));

  // Update underline button
  document
    .getElementById("underline")
    .classList.toggle("active", document.queryCommandState("underline"));

  // Update alignment buttons
  document
    .getElementById("align-left")
    .classList.toggle("active", document.queryCommandState("justifyLeft"));
  document
    .getElementById("align-center")
    .classList.toggle("active", document.queryCommandState("justifyCenter"));
  document
    .getElementById("align-right")
    .classList.toggle("active", document.queryCommandState("justifyRight"));
  document
    .getElementById("align-justify")
    .classList.toggle("active", document.queryCommandState("justifyFull"));

  // Update list buttons
  document
    .getElementById("bullet-list")
    .classList.toggle(
      "active",
      document.queryCommandState("insertUnorderedList")
    );
  document
    .getElementById("numbered-list")
    .classList.toggle(
      "active",
      document.queryCommandState("insertOrderedList")
    );
}

updateWordCount();
updateDocumentName();
