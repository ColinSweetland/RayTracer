JC 		:= javac
JFLAGS	:= -g

SRC_DIR := src
SOURCES := $(shell find -iname "*.java" -path "./$(SRC_DIR)/*" -printf "%P\n")

OBJ_DIR := obj
JFLAGS  += -d $(OBJ_DIR)

all: classes

run: all
	echo "============= running program =============";
	cd obj/ && java RayCaster > ../out.ppm; \
	echo "============= Exit code: $$? ================"
	sxiv out.ppm

classes: $(OBJ_DIR)
	$(JC) $(JFLAGS) -cp $(OBJ_DIR) $(SOURCES)

# make these directories, if they don't exist
$(OBJ_DIR):
	mkdir -p $@

clean:
	$(RM) -r $(OBJ_DIR)

.PHONY: clean
.SILENT: run;