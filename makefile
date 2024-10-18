LIBS := \
    example/common \
    example/orders-service \
    example/consumer \
    example/warehouse-service

clean: $(addsuffix _clean, $(LIBS))

libs: $(addsuffix _obj, $(LIBS))

test: $(addsuffix _test, $(LIBS))

.PHONY: $(%_obj)
%_obj:
	@echo "Installing $(@:%_obj=%)"
	@mvn -f $(@:%_obj=%) clean install

.PHONY: $(%_clean)
%_clean:
	@echo "Cleaning $(@:%_clean=%)"
	@mvn -f $(@:%_clean=%) clean

.PHONY: $(%_test)
%_test:
	@echo "Testing $(@:%_test=%)"
	@mvn -f $(@:%_test=%) test
