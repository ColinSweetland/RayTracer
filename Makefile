JC 		:= javac
JFLAGS	:= -g

SRC_DIR := src
SOURCES := $(shell find -iname "*.java" -path "./$(SRC_DIR)/*" -printf "%P\n")

OBJ_DIR := obj
JFLAGS  += -d $(OBJ_DIR)

# VPATH is where make can search for pattern prereqs (%.cpp) (command outputs src/ and all subdirs)
VPATH 	:= $(shell find $(SRC_DIR)/ -type d -exec printf " %s" {} \;)

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

all: classes

run: all
	echo "============= running program =============";
	cd obj/ && java main > ../out.ppm; \
	echo "============= Exit code: $$? ================"
	sxiv out.ppm

classes: $(OBJ_DIR) $(SOURCES:.java=.class)

# make these directories, if they don't exist
$(OBJ_DIR):
	mkdir -p $@

clean:
	$(RM) *.class

.PHONY: clean
.SILENT: run;