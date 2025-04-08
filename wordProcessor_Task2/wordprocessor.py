import tkinter as tk
from tkinter import filedialog, messagebox, font

class WordProcessor:
    def __init__(self, root):
        self.root = root
        self.root.title("Simple Word Processor")
        self.text_area = tk.Text(self.root)
        self.text_area.pack(fill=tk.BOTH, expand=1)
        self.create_menu()
        self.current_file = None
        
    def create_menu(self):
        menubar = tk.Menu(self.root)
        
        # File menu
        file_menu = tk.Menu(menubar, tearoff=0)
        file_menu.add_command(label="New", command=self.new_file)
        file_menu.add_command(label="Open", command=self.open_file)
        file_menu.add_command(label="Save", command=self.save_file)
        file_menu.add_separator()
        file_menu.add_command(label="Exit", command=self.exit_app)
        menubar.add_cascade(label="File", menu=file_menu)
        
        # Edit menu
        edit_menu = tk.Menu(menubar, tearoff=0)
        edit_menu.add_command(label="Cut", command=self.cut_text)
        edit_menu.add_command(label="Copy", command=self.copy_text)
        edit_menu.add_command(label="Paste", command=self.paste_text)
        menubar.add_cascade(label="Edit", menu=edit_menu)
        
        # Format menu
        format_menu = tk.Menu(menubar, tearoff=0)
        format_menu.add_command(label="Font", command=self.change_font)
        menubar.add_cascade(label="Format", menu=format_menu)
        
        self.root.config(menu=menubar)
    
    def new_file(self):
        self.text_area.delete(1.0, tk.END)
        self.current_file = None
    
    def open_file(self):
        file_path = filedialog.askopenfilename()
        if file_path:
            with open(file_path, 'r') as file:
                content = file.read()
                self.text_area.delete(1.0, tk.END)
                self.text_area.insert(1.0, content)
            self.current_file = file_path
    
    def save_file(self):
        if self.current_file:
            with open(self.current_file, 'w') as file:
                file.write(self.text_area.get(1.0, tk.END))
        else:
            self.save_as_file()
    
    def save_as_file(self):
        file_path = filedialog.asksaveasfilename(defaultextension=".txt")
        if file_path:
            with open(file_path, 'w') as file:
                file.write(self.text_area.get(1.0, tk.END))
            self.current_file = file_path
    
    def exit_app(self):
        if messagebox.askokcancel("Exit", "Do you want to exit?"):
            self.root.destroy()
    
    def cut_text(self):
        self.text_area.event_generate("<<Cut>>")
    
    def copy_text(self):
        self.text_area.event_generate("<<Copy>>")
    
    def paste_text(self):
        self.text_area.event_generate("<<Paste>>")
    
    def change_font(self):
        # Font dialog implementation would go here
        pass

if __name__ == "__main__":
    root = tk.Tk()
    app = WordProcessor(root)
    root.mainloop()